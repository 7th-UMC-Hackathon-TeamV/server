package banban.springboot.web.dto.response;

import banban.springboot.domain.entity.TeamGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {
    private Long id;
    private String username;
    private TeamGroup teamGroup;
}