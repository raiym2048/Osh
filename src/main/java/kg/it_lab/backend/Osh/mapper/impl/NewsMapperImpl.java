package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.news.NewsDetailResponse;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.news.NewsResponse;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.News;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.mapper.NewsMapper;
import kg.it_lab.backend.Osh.repository.CategoryRepository;
import kg.it_lab.backend.Osh.repository.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class NewsMapperImpl implements NewsMapper {
    private final CategoryRepository categoryRepository;
    private final NewsRepository newsRepository;

    @Override
    public NewsResponse toDto(News news) {
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setId(news.getId());
        if(news.getImage() != null)
            newsResponse.setImagePath(news.getImage().getPath());
        newsResponse.setName(news.getName());
        newsResponse.setSlogan(news.getSlogan());
        newsResponse.setCreatedAt(news.getCreatedAt());
        newsResponse.setCategory(news.getCategory().getName());
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
        response.setSlogan(news.getSlogan());
        response.setCreatedAt(news.getCreatedAt());
        response.setCategory(news.getCategory().getName());
        response.setDescription(news.getDescription());
        return  response;
    }


    @Override
    public News toDtoNews(News news, NewsRequest newsRequest , Image image) {
        news.setName(newsRequest.getName());
        news.setDescription(newsRequest.getDescription());
        news.setCreatedAt(LocalDateTime.now());
        news.setCategory(categoryRepository.findById(newsRequest.getCategoryId()).get());
        news.setSlogan(newsRequest.getSlogan());
        news.setImage(image);
        return news;
    }
}
