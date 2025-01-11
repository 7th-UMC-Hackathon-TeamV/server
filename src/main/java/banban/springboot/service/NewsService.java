package banban.springboot.service;

import banban.springboot.apiPayload.code.status.ErrorStatus;
import banban.springboot.apiPayload.exception.GeneralException;
import banban.springboot.apiPayload.exception.handler.NewsHandler;
import banban.springboot.domain.entity.Member;
import banban.springboot.domain.entity.News;
import banban.springboot.domain.entity.TeamGroup;
import banban.springboot.repository.GroupRepository;
import banban.springboot.repository.MemberRepository;
import banban.springboot.repository.NewsRepository;
import banban.springboot.s3.AmazonS3Manager;
import banban.springboot.s3.Uuid;
import banban.springboot.s3.UuidRepository;
import banban.springboot.web.dto.request.NewsRequestDTO;
import banban.springboot.web.dto.response.NewsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {
    private final NewsRepository newsRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;

    @Transactional
    public NewsResponseDTO.NewsCreateResponseDTO createNews(String groupKey, Long memberId, NewsRequestDTO newsRequestDTO, MultipartFile thumbnail_img) {

        TeamGroup teamGroup = groupRepository.findByGroupKey(groupKey)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAMGROUP_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());

        String pictureUrl = s3Manager.uploadFile(s3Manager.generateNewsKeyName(savedUuid), thumbnail_img);


        News news = News.builder()

                .teamGroup(teamGroup)
                .member(member)
                .headline(newsRequestDTO.getHeadline())
                .content(newsRequestDTO.getContent())
                .isBreakingNews(newsRequestDTO.isBreakingNews())
                .likes(0)
                .newsCategories(newsRequestDTO.getNewsCategories())
                .createdAt(newsRequestDTO.getCreatedAt())
                .thumbnail_URL(pictureUrl)
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

    @Transactional
    public NewsResponseDTO.NewsReadResponseDTO readNews(String groupKey, Long newsId) {

        TeamGroup teamGroup = groupRepository.findByGroupKey(groupKey)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAMGROUP_NOT_FOUND));

        News news = newsRepository.findByTeamGroupAndId(teamGroup, newsId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NEWS_NOT_EXIST_FOUND));

        return NewsResponseDTO.NewsReadResponseDTO.from(news);
    }

    @Transactional
    public Void removeNews(String groupKey, Long memberId, Long newsId) {

        TeamGroup teamGroup = groupRepository.findByGroupKey(groupKey)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAMGROUP_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        News news = newsRepository.findByTeamGroupAndMemberAndId(teamGroup, member, newsId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NEWS_NOT_EXIST_FOUND));

        newsRepository.deleteById(news.getId());

        return null;
    }

    /**
     * 특정 그룹의 속보 뉴스 목록 조회
     */
    public List<NewsResponseDTO.NewsReadResponseDTO> getBreakingNewsByGroupKey(String groupKey) {
        //그룹 조회
        TeamGroup teamGroup = groupRepository.findByGroupKey(groupKey)
                .orElseThrow(() -> new RuntimeException("그룹키가 없습니다."));

        //속보 뉴스 조회
        List<News> breakingNewsList = newsRepository.findByTeamGroupAndIsBreakingNewsTrue(teamGroup);

        // News -> NewReadResponseDTO 변환
        return breakingNewsList.stream()
                .map(NewsResponseDTO.NewsReadResponseDTO::from)
                .toList();
    }


    /**
     * 특정 그룹의 일반 뉴스 목록 조회
     */
    public List<NewsResponseDTO.NewsReadResponseDTO> getRegularNewsByGroupKey(String groupKey) {
        //그룹 조회
        TeamGroup teamGroup = groupRepository.findByGroupKey(groupKey)
                .orElseThrow(() -> new RuntimeException("그룹키가 없습니다."));

        //일반 뉴스 조회
        List<News> regularNewsList = newsRepository.findByTeamGroupAndIsBreakingNewsFalse(teamGroup);

        // News -> NewReadResponseDTO 변환
        return regularNewsList.stream()
                .map(NewsResponseDTO.NewsReadResponseDTO::from)
                .toList();
    }
}
