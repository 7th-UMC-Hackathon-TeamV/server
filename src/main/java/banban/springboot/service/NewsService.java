package banban.springboot.service;

import banban.springboot.apiPayload.code.status.ErrorStatus;
import banban.springboot.apiPayload.exception.handler.NewsHandler;
import banban.springboot.domain.entity.News;
import banban.springboot.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    // 뉴스 공감 누르기
    public News addNewsLike(Long newsId){
        News existNews = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsHandler(ErrorStatus.NEWS_NOT_EXIST_FOUND));

        Integer likes_count = existNews.getLikes() + 1;

        existNews.setLikes(likes_count);

        return newsRepository.save(existNews);
    }
}
