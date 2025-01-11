package banban.springboot.service;

import banban.springboot.apiPayload.code.status.ErrorStatus;
import banban.springboot.apiPayload.exception.GeneralException;
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
}
