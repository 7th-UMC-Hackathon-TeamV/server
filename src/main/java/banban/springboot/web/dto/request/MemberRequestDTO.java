package banban.springboot.web.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberRequestDTO {

    @Size(max=10)
    private String username;

    @Size(max=20)
    private String password;
}
