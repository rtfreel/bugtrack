package rt.bugtrack.core.bug.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rt.bugtrack.auth.dto.UserDTO;
import rt.bugtrack.core.bug.util.Status;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BugDTO {
    private Integer id;
    private Integer projectId;
    private UserDTO reporter;
    private List<StepDTO> steps;
    private Status status;
    private String title;
    private String description;
    private Boolean editable;
}

