package banban.springboot.web.dto.response;

import banban.springboot.domain.entity.News;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
        private LocalDateTime createdAt;
        //private List<MultipartFile> images;
        private boolean isBreakingNews;

        public static NewsCreateResponseDTO from(News news) {
            return NewsCreateResponseDTO.builder()
                    .newsId(news.getId())
                    .headline(news.getHeadline())
                    .content(news.getContent())
                    .isBreakingNews(news.isBreakingNews())
                    .createdAt(news.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class NewsTodayResponseDTO {
        private Long newsId;
        private String headline;
        private String content;
        private boolean isBreakingNews;
        private int likes;
        private LocalDateTime createdAt;

        public static NewsTodayResponseDTO from(News news) {
            return NewsTodayResponseDTO.builder()
                    .newsId(news.getId())
                    .headline(news.getHeadline())
                    .content(news.getContent())
                    .isBreakingNews(news.isBreakingNews())
                    .likes(news.getLikes())
                    .createdAt(news.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class NewsYesterdayResponseDTO {
        private Long newsId;
        private String headline;
        private String content;
        private boolean isBreakingNews;
        private int likes;
        private LocalDateTime createdAt;

        public static NewsYesterdayResponseDTO from(News news) {
            return NewsYesterdayResponseDTO.builder()
                    .newsId(news.getId())
                    .headline(news.getHeadline())
                    .content(news.getContent())
                    .isBreakingNews(news.isBreakingNews())
                    .likes(news.getLikes())
                    .createdAt(news.getCreatedAt())
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

        public static NewsReadResponseDTO from(News news) {
            return NewsReadResponseDTO.builder()
                    .newsId(news.getId())
                    .headline(news.getHeadline())
                    .content(news.getContent())
                    .username(news.getMember().getUsername())
                    .isBreakingNews(news.isBreakingNews())
                    .build();
        }
    }
}
