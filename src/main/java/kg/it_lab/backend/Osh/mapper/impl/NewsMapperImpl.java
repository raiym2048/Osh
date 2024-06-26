package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.news.NewsDetailResponse;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.news.NewsResponse;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.News;
import kg.it_lab.backend.Osh.mapper.NewsMapper;
import kg.it_lab.backend.Osh.repository.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsResponse toDto(News news) {
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setId(news.getId());
        if(news.getImage() != null)
            newsResponse.setImagePath(news.getImage().getPath());
        newsResponse.setName(news.getName());
        newsResponse.setCreatedAt(news.getCreatedAt());
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
        if(news.getImage() != null)
            response.setImagePath(news.getImage().getPath());
        response.setName(news.getName());
        response.setCreatedAt(news.getCreatedAt());
        response.setDescription(news.getDescription());
        return  response;
    }


    @Override
    public News toDtoNews(News news, NewsRequest newsRequest , Image image) {
        news.setName(newsRequest.getName());
        news.setDescription(newsRequest.getDescription());
        news.setCreatedAt(LocalDateTime.now());
        news.setImage(image);
        return news;
    }
}
