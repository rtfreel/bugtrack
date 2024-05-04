package rt.bugtrack.controller;

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
import rt.bugtrack.core.bug.dto.BugDTO;
import rt.bugtrack.core.bug.dto.BugHistoryDTO;
import rt.bugtrack.core.bug.service.BugService;
import rt.bugtrack.core.project.dto.BugListRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bug")
public class BugController {
    private final BugService bugService;

    @GetMapping(value = "/{id}")
    public BugDTO getBug(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Integer id) {
        return bugService.getBug((User)details, id);
    }

    @GetMapping(value = "/{id}/history")
    public List<BugHistoryDTO> getBugHistory(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Integer id) {
        return bugService.getBugHistory(id);
    }

    @PostMapping(value = { "/list/project={projectId}/all" })
    public List<BugDTO> getProjectBugs(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Integer projectId,
            @RequestBody BugListRequest query) {
        return bugService.getProjectBugs((User)details, projectId, query.getStatuses(), query.getQuery());
    }

    @PostMapping(value = { "/list/project={projectId}/reported" })
    public List<BugDTO> getReportedProjectBugs(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Integer projectId,
            @RequestBody BugListRequest query) {
        return bugService.getReportedProjectBugs((User)details, projectId, query.getStatuses(), query.getQuery());
    }

    @PostMapping(value = "/")
    public BugDTO createBug(
            @AuthenticationPrincipal UserDetails details,
            @RequestBody BugDTO project) {
        return bugService.createBug((User)details, project);
    }

    @PutMapping(value = "/{id}")
    public BugDTO updateBug(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Integer id, 
            @RequestBody BugDTO project) {
        return bugService.updateBug((User)details, id, project);
    }
}
