package rt.bugtrack.core.project.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rt.bugtrack.core.bug.util.Status;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BugListRequest {
    private String query;
    private List<Status> statuses;
}
