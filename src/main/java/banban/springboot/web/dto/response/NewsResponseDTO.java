package banban.springboot.web.dto.response;

import banban.springboot.domain.entity.News;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class NewsResponseDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class NewsCreateResponseDTO {
        private Long newsId;
        private String headline;
        private String content;
        //private List<MultipartFile> images;
        private boolean isBreakingNews;

        public static NewsCreateResponseDTO from(News news) {
            return NewsCreateResponseDTO.builder()
                    .newsId(news.getId())
                    .headline(news.getHeadline())
                    .content(news.getContent())
                    .isBreakingNews(news.isBreakingNews())
                    .build();
        }
    }
}
