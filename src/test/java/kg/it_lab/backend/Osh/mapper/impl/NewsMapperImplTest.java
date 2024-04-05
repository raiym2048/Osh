package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.news.NewsDetailResponse;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.news.NewsResponse;
import kg.it_lab.backend.Osh.entities.Category;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.News;
import kg.it_lab.backend.Osh.repository.CategoryRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsMapperImplTest {

    @InjectMocks
    private NewsMapperImpl newsMapper;

    @Mock
    private CategoryRepository categoryRepository;

    private final News news = new News();
    private final Image image = new Image();
    private final Category category = new Category();

    @BeforeEach
    void setUp() {
        category.setId(1L);
        category.setName("category");
        category.setEvents(null);

        image.setId(1L);
        image.setName("name");
        image.setPath("path");

        news.setId(1L);
        news.setName("name");
        news.setDescription("description");
        news.setCreatedAt(LocalDateTime.now());
        news.setSlogan("slogan");
        news.setCategory(category);
        news.setImage(image);
    }

    @Test
    void toDto() {
        NewsResponse responseResult = newsMapper.toDto(news);

        assertEquals(news.getId(), responseResult.getId());
        assertEquals(news.getImage().getPath(), responseResult.getImagePath());
        assertEquals(news.getName(), responseResult.getName());
        assertEquals(news.getCreatedAt(), responseResult.getCreatedAt());
        assertEquals(news.getSlogan(), responseResult.getSlogan());
        assertEquals(news.getCategory().getName(), responseResult.getCategory());
    }

    @Test
    void toDtos() {
        Category category1 = new Category();
        category1.setId(2L);
        category1.setName("category1");
        category1.setEvents(null);

        Image image1 = new Image();
        image1.setId(1L);
        image1.setName("name1");
        image1.setPath("path1");

        News news1 = new News();
        news1.setId(2L);
        news1.setName("name");
        news1.setDescription("description");
        news1.setCreatedAt(LocalDateTime.now());
        news1.setSlogan("slogan");
        news1.setCategory(category1);
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
        assertEquals(news.getSlogan(), detailResponseResult.getSlogan());
        assertEquals(news.getCategory().getName(), detailResponseResult.getCategory());
        assertEquals(news.getDescription(), detailResponseResult.getDescription());
    }

    @Test
    void toDtoNews() {
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setName("name");
        newsRequest.setDescription("description");
        newsRequest.setCategoryId(1L);
        newsRequest.setSlogan("slogan");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        News newsResult = newsMapper.toDtoNews(new News(), newsRequest, image);

        assertEquals(newsRequest.getName(), newsResult.getName());
        assertEquals(newsRequest.getDescription(), newsResult.getDescription());
        assertEquals(newsRequest.getCategoryId(), newsResult.getCategory().getId());
        assertEquals(newsRequest.getSlogan(), newsResult.getSlogan());
        assertEquals(category, newsResult.getCategory());
        assertEquals(image, newsResult.getImage());
    }

    @Test
    void toDtoNewsThrowExceptionWhenCategoryNotFound() {
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setName("name");
        newsRequest.setDescription("description");
        newsRequest.setCategoryId(1L);
        newsRequest.setSlogan("slogan");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> newsMapper.toDtoNews(new News(), newsRequest, image));
    }
}