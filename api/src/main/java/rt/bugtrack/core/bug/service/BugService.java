package rt.bugtrack.core.bug.service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rt.bugtrack.auth.entity.User;
import rt.bugtrack.auth.service.UserService;
import rt.bugtrack.core.bug.dto.BugDTO;
import rt.bugtrack.core.bug.dto.BugHistoryDTO;
import rt.bugtrack.core.bug.dto.StepDTO;
import rt.bugtrack.core.bug.entity.Bug;
import rt.bugtrack.core.bug.entity.BugHistory;
import rt.bugtrack.core.bug.entity.Step;
import rt.bugtrack.core.bug.repository.BugHistoryRepository;
import rt.bugtrack.core.bug.repository.BugRepository;
import rt.bugtrack.core.bug.repository.StepRepository;
import rt.bugtrack.core.bug.util.Status;
import rt.bugtrack.core.project.dto.ProjectDTO;
import rt.bugtrack.core.project.service.ProjectService;

@Service
@RequiredArgsConstructor
public class BugService {
    private final UserService userService;

    private final BugRepository bugRepository;
    private final StepRepository stepRepository;
    private final BugHistoryRepository bugHistoryRepository;

    private final ProjectService projectService;

    private boolean isEditable(User user, Bug bug) {
        return user.getId() == bug.getReporter().getId()
                || projectService.getProject(user, bug.getProjectId()).getEditable();
    }

    public BugDTO getBugDTO(User user, Bug bug) {
        return BugDTO.builder()
                .id(bug.getId())
                .reporter(userService.getUserDTO(bug.getReporter()))
                .projectId(bug.getProjectId())
                .status(bug.getStatus())
                .title(bug.getTitle())
                .description(bug.getDescription())
                .steps(bug.getSteps().stream()
                        .map(step -> 
                            StepDTO.builder()
                                .id(step.getId())
                                .bugId(step.getBug().getId())
                                .sortOrder(step.getSortOrder())
                                .description(step.getDescription())
                                .expectedResult(step.getExpectedResult())
                                .actualResult(step.getActualResult())
                                .build())
                        .toList())
                .editable(isEditable(user, bug))
                .build();
    }

    public BugHistoryDTO getBugHistoryDTO(BugHistory bugHistory) {
        return BugHistoryDTO.builder()
                .id(bugHistory.getId())
                .user(userService.getUserDTO(bugHistory.getUser()))
                .bugId(bugHistory.getBugId())
                .status(bugHistory.getStatus())
                .title(bugHistory.getTitle())
                .description(bugHistory.getDescription())
                .stepsUpdated(bugHistory.getStepsUpdated())
                .changeTime(bugHistory.getChangeTime())
                .build();
    }

    public BugDTO getBug(User user, Integer id) {
        Optional<Bug> bug = bugRepository.findById(id);
        if (bug.isEmpty()) {
            throw new RuntimeException("Bug not found");
        }
        return getBugDTO(user, bug.get());
    }

    public List<BugDTO> getReportedBugs(User user, List<Status> statuses, String query) {
        return bugRepository.findByReporterAndStatusInAndTitleContainingIgnoreCase(user, statuses, query).stream()
                .map(bug -> getBugDTO(user, bug))
                .toList();
    }

    public List<BugDTO> getProjectBugs(User user, Integer projectId, List<Status> statuses, String query) {
        return bugRepository.findByProjectIdAndStatusInAndTitleContainingIgnoreCase(projectId, statuses, query).stream()
                .map(bug -> getBugDTO(user, bug))
                .toList();
    }

    public List<BugDTO> getReportedProjectBugs(User user, Integer projectId, List<Status> statuses, String query) {
        return bugRepository.findByProjectIdAndReporterAndStatusInAndTitleContainingIgnoreCase(projectId, user, statuses, query).stream()
                .map(bug -> getBugDTO(user, bug))
                .toList();
    }

    public List<BugHistoryDTO> getBugHistory(Integer id) {
        return bugHistoryRepository.findByBugId(id).stream()
                .map(bugHistory -> getBugHistoryDTO(bugHistory))
                .toList();
    }

    public BugDTO createBug(User user, BugDTO bug) {
        Bug newBug = bugRepository.save(Bug.builder()
                .reporter(user)
                .projectId(bug.getProjectId())
                .status(Status.NEW)
                .title(bug.getTitle())
                .description(bug.getDescription())
                .build());
        bug.getSteps().forEach(step -> {
            Step newStep = Step.builder()
                    .bug(newBug)
                    .sortOrder(step.getSortOrder())
                    .description(step.getDescription())
                    .expectedResult(step.getExpectedResult())
                    .actualResult(step.getActualResult())
                    .build();
            newBug.getSteps().add(stepRepository.save(newStep));
        });
        Bug createdBug = bugRepository.save(newBug);
        BugHistory bugHistory = BugHistory.builder()
                .user(user)
                .bugId(createdBug.getId())
                .status(Status.NEW)
                .title(createdBug.getTitle())
                .description(createdBug.getDescription())
                .stepsUpdated(false)
                .changeTime(new Date())
                .build();
        bugHistoryRepository.save(bugHistory);
        return getBugDTO(user, createdBug);
    }

    public BugDTO updateBug(User user, Integer id, BugDTO bug) {
        Bug existingBug = bugRepository.findById(id).orElseThrow(() -> new RuntimeException("Bug not found"));
        if (!isEditable(user, existingBug)) {
            throw new RuntimeException("Bug not editable");
        }
        // process steps
        List<Step> existingSteps = existingBug.getSteps();
        List<Step> newSteps = bug.getSteps().stream()
                .map(step -> Step.builder()
                        .id(step.getId())
                        .bug(existingBug)
                        .sortOrder(step.getSortOrder())
                        .description(step.getDescription())
                        .expectedResult(step.getExpectedResult())
                        .actualResult(step.getActualResult())
                        .build())
                .toList();
        // remove
        AtomicBoolean stepsUpdated = new AtomicBoolean(false);
        int stepsCount = existingSteps.size();
        existingSteps.removeIf(step -> newSteps.stream()
                .noneMatch(newStep -> newStep.getId().equals(step.getId())));
        stepsUpdated.set(stepsCount != existingSteps.size());
        // add or update
        newSteps.forEach(step -> {
            Optional<Step> existingStep = existingSteps.stream()
                    .filter(s -> s.getId().equals(step.getId()))
                    .findFirst();
            // add
            if (existingStep.isEmpty()) {
                existingSteps.add(stepRepository.save(step));
                stepsUpdated.set(true);
            }
            // update
            else {
                if (!existingStep.get().getDescription().equals(step.getDescription())
                        || !existingStep.get().getSortOrder().equals(step.getSortOrder())
                        || !existingStep.get().getExpectedResult().equals(step.getExpectedResult())
                        || !existingStep.get().getActualResult().equals(step.getActualResult())) {
                    existingSteps.removeIf(s -> s.getId().equals(step.getId()));
                    existingSteps.add(stepRepository.save(step));
                    stepsUpdated.set(true);
                }
            }
        });

        Bug updatedBug = existingBug;
        if (stepsUpdated.get()
                || existingBug.getTitle().equals(bug.getTitle())
                || existingBug.getDescription().equals(bug.getDescription())
                || existingBug.getStatus().equals(bug.getStatus())) {
            existingBug.setTitle(bug.getTitle());
            existingBug.setDescription(bug.getDescription());
            existingBug.setStatus(bug.getStatus());
            updatedBug = bugRepository.save(existingBug);
            // history
            BugHistory bugHistory = BugHistory.builder()
                    .user(user)
                    .bugId(updatedBug.getId())
                    .status(updatedBug.getStatus())
                    .title(updatedBug.getTitle())
                    .description(updatedBug.getDescription())
                    .stepsUpdated(stepsUpdated.get())
                    .changeTime(new Date())
                    .build();
            bugHistoryRepository.save(bugHistory);
        }
        return getBugDTO(user, updatedBug);
    }
}
