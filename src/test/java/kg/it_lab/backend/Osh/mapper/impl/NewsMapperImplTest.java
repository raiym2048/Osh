package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.news.NewsDetailResponse;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.news.NewsResponse;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.News;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NewsMapperImplTest {

    @InjectMocks
    private NewsMapperImpl newsMapper;

    private final News news = new News();
    private final Image image = new Image();

    @BeforeEach
    void setUp() {

        image.setId(1L);
        image.setName("name");
        image.setPath("path");

        news.setId(1L);
        news.setName("name");
        news.setDescription("description");
        news.setCreatedAt(LocalDateTime.now());
        news.setImage(image);
    }

    @Test
    void toDto() {
        NewsResponse responseResult = newsMapper.toDto(news);

        assertEquals(news.getId(), responseResult.getId());
        assertEquals(news.getImage().getPath(), responseResult.getImagePath());
        assertEquals(news.getName(), responseResult.getName());
        assertEquals(news.getCreatedAt(), responseResult.getCreatedAt());
    }

    @Test
    void toDtos() {

        Image image1 = new Image();
        image1.setId(1L);
        image1.setName("name1");
        image1.setPath("path1");

        News news1 = new News();
        news1.setId(2L);
        news1.setName("name");
        news1.setDescription("description");
        news1.setCreatedAt(LocalDateTime.now());
        news1.setImage(image1);

        List<News> newsList = new ArrayList<>();
        newsList.add(news);
        newsList.add(news1);

        List<NewsResponse> responseListResult = newsMapper.toDtos(newsList);

        assertEquals(newsList.size(), responseListResult.size());
        assertEquals(newsList.get(0).getId(), responseListResult.get(0).getId());
        assertEquals(newsList.get(1).getImage().getPath(), responseListResult.get(1).getImagePath());
    }

    @Test
    void toDetailDto() {
        NewsDetailResponse detailResponseResult = newsMapper.toDetailDto(news);

        assertEquals(news.getId(), detailResponseResult.getId());
        assertEquals(news.getImage().getPath(), detailResponseResult.getImagePath());
        assertEquals(news.getName(), detailResponseResult.getName());
        assertEquals(news.getCreatedAt(), detailResponseResult.getCreatedAt());
        assertEquals(news.getDescription(), detailResponseResult.getDescription());
    }

    @Test
    void toDtoNews() {
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setName("name");
        newsRequest.setDescription("description");

        News newsResult = newsMapper.toDtoNews(new News(), newsRequest, image);

        assertEquals(newsRequest.getName(), newsResult.getName());
        assertEquals(newsRequest.getDescription(), newsResult.getDescription());
        assertEquals(image, newsResult.getImage());
    }

    @Test
    void toDtoNewsThrowExceptionWhenCategoryNotFound() {
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setName("name");
        newsRequest.setDescription("description");

        assertThrows(NoSuchElementException.class, () -> newsMapper.toDtoNews(new News(), newsRequest, image));
    }
}