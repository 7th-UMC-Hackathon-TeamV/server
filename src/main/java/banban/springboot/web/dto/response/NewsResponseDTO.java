package banban.springboot.web.dto.response;

import banban.springboot.domain.entity.News;
import banban.springboot.domain.enums.NewsCategories;
import banban.springboot.domain.enums.NewsCategories;
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
        private String images;
        private boolean isBreakingNews;
        private LocalDateTime createdAt;
        private NewsCategories newsCategories;

        public static NewsCreateResponseDTO from(News news) {
            return NewsCreateResponseDTO.builder()
                    .newsId(news.getId())
                    .headline(news.getHeadline())
                    .content(news.getContent())
                    .isBreakingNews(news.isBreakingNews())
                    .createdAt(news.getCreatedAt())
                    .newsCategories(news.getNewsCategories())
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
        private NewsCategories newsCategories;

        public static NewsTodayResponseDTO from(News news) {
            return NewsTodayResponseDTO.builder()
                    .newsId(news.getId())
                    .headline(news.getHeadline())
                    .content(news.getContent())
                    .isBreakingNews(news.isBreakingNews())
                    .likes(news.getLikes())
                    .createdAt(news.getCreatedAt())
                    .newsCategories(news.getNewsCategories())
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
        private String images;
        private LocalDateTime createdAt;
        private NewsCategories newsCategories;

        public static NewsYesterdayResponseDTO from(News news) {
            return NewsYesterdayResponseDTO.builder()
                    .newsId(news.getId())
                    .headline(news.getHeadline())
                    .content(news.getContent())
                    .isBreakingNews(news.isBreakingNews())
                    .likes(news.getLikes())
                    .newsCategories(news.getNewsCategories())
                    .createdAt(news.getCreatedAt())
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
        private String images;
        private NewsCategories newsCategories;
        private boolean isBreakingNews;

        public static NewsReadResponseDTO from(News news) {
            return NewsReadResponseDTO.builder()
                    .newsId(news.getId())
                    .headline(news.getHeadline())
                    .content(news.getContent())
                    .username(news.getMember().getUsername())
                    .isBreakingNews(news.isBreakingNews())
                    .images(news.getThumbnail_URL())
                    .newsCategories(news.getNewsCategories())
                    .build();
        }
    }
}
