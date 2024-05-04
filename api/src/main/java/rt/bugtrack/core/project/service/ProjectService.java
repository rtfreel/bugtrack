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

    private boolean isEditable(User user, Project project) {
        return user.getId() == project.getOwner().getId();
    }

    public ProjectDTO getProjectDTO(User user, Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .owner(userService.getUserDTO(project.getOwner()))
                .title(project.getTitle())
                .description(project.getDescription())
                .editable(isEditable(user, project))
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
            throw new RuntimeException("Project not found");
        }
        return getProjectDTO(user, project.get());
    }

    public List<ProjectDTO> getProjects(User user, String query) {
        return projectRepository.findByTitleContainingIgnoreCase(query).stream()
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

    public ProjectDTO updateProject(User user, Integer id, ProjectDTO project) {
        Project existingProject = projectRepository.findById(id).orElseThrow();
        if (!isEditable(user, existingProject)) {
            throw new RuntimeException("Project not editable");
        }
        if (existingProject.getTitle().equals(project.getTitle())
                && existingProject.getDescription().equals(project.getDescription())) {
            return getProjectDTO(user, existingProject);
        }
        existingProject.setTitle(project.getTitle());
        existingProject.setDescription(project.getDescription());
        existingProject = projectRepository.save(existingProject);
        ProjectHistory projectHistory = ProjectHistory.builder()
                .user(user)
                .projectId(id)
                .title(project.getTitle())
                .description(project.getDescription())
                .changeTime(new Date())
                .build();
        projectHistoryRepository.save(projectHistory);
        return getProjectDTO(user, existingProject);
    }
}
