package rt.bugtrack.core.bug.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepDTO {
    private Integer id;
    private Integer bugId;
    private Integer sortOrder;
    private String description;
    private String expectedResult;
    private String actualResult;
}
