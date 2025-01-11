package banban.springboot.web.dto.request;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequestDTO {
    private String id;
    @Max(20)
    private String userId;

    @Max(20)
    private String password;
}
