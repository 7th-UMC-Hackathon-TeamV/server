package banban.springboot.domain.entity;

import banban.springboot.domain.enums.NewsCategories;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String headline;;

    @Column(nullable = false, length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    private NewsCategories newsCategories; //긍정,부정

    @ColumnDefault("0")
    @Column(name = "like_count", nullable = false)
    private Integer likes;

    @ElementCollection
    private List<String> thumbnail_URL;

    @Column(nullable = false)
    private boolean isBreakingNews;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

}
