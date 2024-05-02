package rt.bugtrack.core.project.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rt.bugtrack.auth.dto.UserDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectHistoryDTO {
    private Integer id;
    private UserDTO user;
    private Integer projectId;
    private String title;
    private String description;
    private Date changeTime;
}
