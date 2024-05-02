package rt.bugtrack.core.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rt.bugtrack.auth.entity.User;
import rt.bugtrack.core.project.dto.ProjectDTO;
import rt.bugtrack.core.project.dto.ProjectHistoryDTO;
import rt.bugtrack.core.project.service.ProjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping(value = { "/list/all", "/list/all/{query}" })
    public List<ProjectDTO> getAllProjects(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Optional<String> query) {
        if (query.isPresent()) {
            return projectService.getProjects((User)details, query.get());
        }
        return projectService.getProjects((User)details);
    }

    @GetMapping(value = { "/list/my", "/list/my/{query}" })
    public List<ProjectDTO> getMyProjects(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Optional<String> query) {
        if (query.isPresent()) {
            return projectService.getUserProjects((User)details, query.get());
        }
        return projectService.getUserProjects((User)details);
    }

    @GetMapping(value = "/{id}")
    public ProjectDTO getProject(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Integer id) {
        return projectService.getProject((User)details, id);
    }

    @GetMapping(value = "/{id}/history")
    public List<ProjectHistoryDTO> getProjectHistory(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Integer id) {
        return projectService.getProjectHistory(id);
    }

    @PostMapping
    public ProjectDTO createProject(
            @AuthenticationPrincipal UserDetails details,
            @RequestBody ProjectDTO project) {
        return projectService.createProject((User)details, project);
    }

    @PutMapping(value = "/{id}")
    public void updateProject(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Integer id, 
            @RequestBody ProjectDTO project) {
        projectService.updateProject((User)details, id, project);
    }
}
