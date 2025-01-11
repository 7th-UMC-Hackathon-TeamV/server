package banban.springboot.web.dto.request;

import banban.springboot.domain.enums.NewsCategories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class NewsRequestDTO {
    @NotBlank
    @Size(max = 20, message = "뉴스 헤드라인은 최대 20자입니다.")
    private String headline;

    @NotBlank
    @Size(max = 1000, message = "뉴스 본문은 최대 1000자입니다.")
    private String content;

    private List<MultipartFile> images; // 사용자가 업로드한 이미지

    private boolean isBreakingNews; // 속보 여부

    @NotNull(message = "긍정인지 부정인지 작성해주세요")
    private NewsCategories newsCategories;

    private LocalDateTime createdAt = LocalDateTime.now();
}
