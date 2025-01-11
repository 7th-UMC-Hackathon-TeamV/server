package banban.springboot.service;

import banban.springboot.apiPayload.code.status.ErrorStatus;
import banban.springboot.apiPayload.exception.GeneralException;
import banban.springboot.apiPayload.exception.handler.NewsHandler;
import banban.springboot.domain.entity.Member;
import banban.springboot.domain.entity.News;
import banban.springboot.repository.MemberRepository;
import banban.springboot.repository.NewsRepository;
import banban.springboot.web.dto.request.NewsRequestDTO;
import banban.springboot.web.dto.response.NewsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {
    private final NewsRepository newsRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public NewsResponseDTO.NewsCreateResponseDTO createNews(Long groupId, Long memberId, NewsRequestDTO newsRequestDTO) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        News news = News.builder()

                .member(member)
                .headline(newsRequestDTO.getHeadline())
                .content(newsRequestDTO.getContent())
                .isBreakingNews(newsRequestDTO.isBreakingNews())
                .likes(0)
                .newsCategories(newsRequestDTO.getNewsCategories())
                .createdAt(newsRequestDTO.getCreatedAt())
                .build();

        news = newsRepository.save(news);
        return NewsResponseDTO.NewsCreateResponseDTO.from(news);
    }
    // 뉴스 공감 누르기
    public News addNewsLike(Long newsId){
        News existNews = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsHandler(ErrorStatus.NEWS_NOT_EXIST_FOUND));

        Integer likes_count = existNews.getLikes() + 1;

        existNews.setLikes(likes_count);

        return newsRepository.save(existNews);
    }

}
