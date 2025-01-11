package banban.springboot.web.dto.request;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberRequestDTO {

    @Max(10)
    private String username;

    @Max(20)
    private String password;
}
