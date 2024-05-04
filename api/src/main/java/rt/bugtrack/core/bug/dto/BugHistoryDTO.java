package rt.bugtrack.core.bug.dto;

import java.util.Date;

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
public class BugHistoryDTO {
    private Integer id;
    private UserDTO user;
    private Integer bugId;
    private Status status;
    private String title;
    private String description;
    private Boolean stepsUpdated;
    private Date changeTime;
}
