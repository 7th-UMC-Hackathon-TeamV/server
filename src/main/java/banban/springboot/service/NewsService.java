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
import banban.springboot.web.controller.NewsTestController;
import banban.springboot.web.dto.request.NewsRequestDTO;
import banban.springboot.web.dto.response.NewsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {
    private final NewsRepository newsRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;
    //private final Environment environment;

    @Transactional
    public NewsResponseDTO.NewsCreateResponseDTO createNews(String groupKey, Long memberId, NewsRequestDTO newsRequestDTO) {

        TeamGroup teamGroup = groupRepository.findByGroupKey(groupKey)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAMGROUP_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        News news = News.builder()

                .teamGroup(teamGroup)
                .member(member)
                .headline(newsRequestDTO.getHeadline())
                .content(newsRequestDTO.getContent())
                .isBreakingNews(newsRequestDTO.isBreakingNews())
                .likes(0)
                .newsCategories(newsRequestDTO.getNewsCategories())
                //.createdAt(newsRequestDTO.getCreatedAt())
                .createdAt(getCurrentTime())
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

//    private LocalDateTime getCurrentTime() {
//        if (isTestMode()) {
//            return NewsTestController.getMockCurrentTime();
//        }
//        return LocalDateTime.now();
//    }

//    private boolean isTestMode() {
//        return Arrays.asList(environment.getActiveProfiles()).contains("test");
//    }

    private LocalDateTime getCurrentTime() {
        return NewsTestController.getMockCurrentTime();  // 항상 테스트 시간 반환
    }

    public List<NewsResponseDTO.NewsTodayResponseDTO> getTodayNews(String groupKey, Long memberId) {
        // 그룹과 멤버 존재 확인
        TeamGroup teamGroup = groupRepository.findByGroupKey(groupKey)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAMGROUP_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        //LocalDateTime now = LocalDateTime.now();
        LocalDateTime now = getCurrentTime();
//        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime startOfDay = now.minusDays(1).withHour(18).withMinute(0).withSecond(0);
        LocalDateTime endOfToday = now.toLocalDate().atTime(17, 59, 59);

        List<News> todayNews = newsRepository.findByTeamGroupAndCreatedAtBetween(
                teamGroup, startOfDay, endOfToday);

        return todayNews.stream()
                .map(NewsResponseDTO.NewsTodayResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<NewsResponseDTO.NewsYesterdayResponseDTO> getYesterdayNews(String groupKey, Long memberId) {
        // 그룹과 멤버 존재 확인
        TeamGroup teamGroup = groupRepository.findByGroupKey(groupKey)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAMGROUP_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        //LocalDateTime now = LocalDateTime.now();
        LocalDateTime now = getCurrentTime();
        LocalDateTime twoYesterdayAt6PM = now.minusDays(1).withHour(00).withMinute(0).withSecond(0);
        LocalDateTime yesterdayAt6PM = now.minusDays(1).withHour(17).withMinute(59).withSecond(59);

        List<News> yesterdayNews = newsRepository.findByTeamGroupAndCreatedAtBetween(
                teamGroup, twoYesterdayAt6PM, yesterdayAt6PM);

        return yesterdayNews.stream()
                .map(NewsResponseDTO.NewsYesterdayResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 18 * * *")
    @Transactional
    public void deleteOldNews() {
        LocalDateTime now = getCurrentTime(); // 수정된 부분
//        LocalDateTime yesterdayAt6PM = now.minusDays(1)
//                .withHour(18).withMinute(0).withSecond(0);
        LocalDateTime twoYesterdayAt6PM = now.minusDays(1).withHour(00).withMinute(0).withSecond(0);
        LocalDateTime yesterdayAt6PM = now.minusDays(1).withHour(17).withMinute(59).withSecond(59);
        newsRepository.deleteByCreatedAtBetween(twoYesterdayAt6PM, yesterdayAt6PM);
    }
}
