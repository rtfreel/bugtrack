package rt.bugtrack.core.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rt.bugtrack.auth.dto.UserDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Integer id;
    private UserDTO owner;
    private String title;
    private String description;
    private Boolean editable;
}

