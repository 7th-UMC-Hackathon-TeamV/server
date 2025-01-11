package banban.springboot.web.dto.response;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NewsResponseDTO {
    private Long id;
    private String headline;
    private String content;
    private List<MultipartFile> images;
    private boolean isBreakingNews;
}
