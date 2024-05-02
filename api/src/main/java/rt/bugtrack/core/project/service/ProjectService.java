package rt.bugtrack.core.project.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rt.bugtrack.auth.entity.User;
import rt.bugtrack.auth.service.UserService;
import rt.bugtrack.core.project.dto.ProjectDTO;
import rt.bugtrack.core.project.dto.ProjectHistoryDTO;
import rt.bugtrack.core.project.entity.Project;
import rt.bugtrack.core.project.entity.ProjectHistory;
import rt.bugtrack.core.project.repository.ProjectHistoryRepository;
import rt.bugtrack.core.project.repository.ProjectRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final UserService userService;

    private final ProjectRepository projectRepository;
    private final ProjectHistoryRepository projectHistoryRepository;

    public ProjectDTO getProjectDTO(User user, Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .owner(userService.getUserDTO(user))
                .title(project.getTitle())
                .description(project.getDescription())
                .editable(user.getId() == project.getOwner().getId())
                .build();
    }

    public ProjectHistoryDTO getProjectHistoryDTO(ProjectHistory projectHistory) {
        return ProjectHistoryDTO.builder()
                .id(projectHistory.getId())
                .user(userService.getUserDTO(projectHistory.getUser()))
                .projectId(projectHistory.getProjectId())
                .title(projectHistory.getTitle())
                .description(projectHistory.getDescription())
                .changeTime(projectHistory.getChangeTime())
                .build();
    }

    public ProjectDTO getProject(User user, Integer id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            return null;
        }
        return getProjectDTO(user, project.get());
    }

    public List<ProjectDTO> getProjects(User user) {
        return projectRepository.findAll().stream()
                .map(project -> getProjectDTO(user, project))
                .toList();
    }

    public List<ProjectDTO> getProjects(User user, String query) {
        return projectRepository.findByTitleContainingIgnoreCase(query).stream()
                .map(project -> getProjectDTO(user, project))
                .toList();
    }

    public List<ProjectDTO> getUserProjects(User user) {
        return projectRepository.findByOwner(user).stream()
                .map(project -> getProjectDTO(user, project))
                .toList();
    }

    public List<ProjectDTO> getUserProjects(User user, String query) {
        return projectRepository.findByOwnerAndTitleContainingIgnoreCase(user, query).stream()
                .map(project -> getProjectDTO(user, project))
                .toList();
    }

    public List<ProjectHistoryDTO> getProjectHistory(int projectId) {
        return projectHistoryRepository.findByProjectIdOrderByChangeTimeDesc(projectId).stream()
                .map(projectHistory -> getProjectHistoryDTO(projectHistory))
                .toList();
    }

    public ProjectDTO createProject(User user, ProjectDTO project) {
        Project newProject = Project.builder()
                .owner(user)
                .title(project.getTitle())
                .description(project.getDescription())
                .build();
        newProject = projectRepository.save(newProject);
        ProjectHistory projectHistory = ProjectHistory.builder()
                .user(user)
                .projectId(newProject.getId())
                .title(newProject.getTitle())
                .description(newProject.getDescription())
                .changeTime(new Date())
                .build();
        projectHistoryRepository.save(projectHistory);
        return getProjectDTO(user, newProject);
    }

    public void updateProject(User user, Integer id, ProjectDTO project) {
        Optional<Project> newProject = projectRepository.findById(id);
        if (newProject.isEmpty()) {
            throw new IllegalArgumentException("Project not found");
        }
        projectRepository.updateProject(id, project.getTitle(), project.getDescription());
        ProjectHistory projectHistory = ProjectHistory.builder()
                .user(user)
                .projectId(id)
                .title(project.getTitle())
                .description(project.getDescription())
                .changeTime(new Date())
                .build();
        projectHistoryRepository.save(projectHistory);
    }
}
