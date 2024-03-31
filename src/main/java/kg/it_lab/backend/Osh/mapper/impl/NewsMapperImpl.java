package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.news.NewsDetailResponse;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.news.NewsResponse;
import kg.it_lab.backend.Osh.entities.News;
import kg.it_lab.backend.Osh.mapper.NewsMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsResponse toDto(News news) {
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setId(news.getId());
        newsResponse.setName(news.getName());
        newsResponse.setSlogan(news.getSlogan());
        newsResponse.setCreatedAt(news.getCreatedAt());
        newsResponse.setCategory(news.getCategory());
        return  newsResponse;

    }

    @Override
    public List<NewsResponse> toDtos(List<News> newsList) {
        List<NewsResponse>newsResponses = new ArrayList<>();
        for(News news : newsList){
            newsResponses.add(toDto(news));
        }
        return newsResponses;
    }

    @Override
    public NewsDetailResponse toDetailDto(News news) {
        NewsDetailResponse response = new NewsDetailResponse();
        response.setId(news.getId());
        response.setName(news.getName());
        response.setSlogan(news.getSlogan());
        response.setCreatedAt(news.getCreatedAt());
        response.setCategory(news.getCategory());
        response.setDescription(news.getDescription());
        return  response;
    }


    @Override
    public News toDtoNews(News news, NewsRequest newsRequest) {
        news.setName(newsRequest.getName());
        news.setDescription(newsRequest.getDescription());
        news.setCreatedAt(LocalDateTime.now());
        news.setCategory(newsRequest.getCategory());
        news.setSlogan(newsRequest.getSlogan());
        return news;
    }
}
