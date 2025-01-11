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
        private String images;
        private boolean isBreakingNews;

        public static NewsCreateResponseDTO from(News news) {
            return NewsCreateResponseDTO.builder()
                    .newsId(news.getId())
                    .headline(news.getHeadline())
                    .content(news.getContent())
                    .isBreakingNews(news.isBreakingNews())
                    .images(news.getThumbnail_URL())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class NewsReadResponseDTO {
        private Long newsId;
        private String headline;
        private String content;
        private String username;
        private boolean isBreakingNews;
        private String images;

        public static NewsReadResponseDTO from(News news) {
            return NewsReadResponseDTO.builder()
                    .newsId(news.getId())
                    .headline(news.getHeadline())
                    .content(news.getContent())
                    .username(news.getMember().getUsername())
                    .isBreakingNews(news.isBreakingNews())
                    .images(news.getThumbnail_URL())
                    .build();
        }
    }
}
