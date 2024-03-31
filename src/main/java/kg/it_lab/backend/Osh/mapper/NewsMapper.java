package kg.it_lab.backend.Osh.mapper;

import kg.it_lab.backend.Osh.dto.news.NewsDetailResponse;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.news.NewsResponse;
import kg.it_lab.backend.Osh.entities.News;

import java.util.List;

public interface NewsMapper {
    NewsResponse toDto(News news);
    List<NewsResponse> toDtos(List<News> news);
    News toDtoNews(News news , NewsRequest newsRequest);

    NewsDetailResponse toDetailDto(News news);
}
