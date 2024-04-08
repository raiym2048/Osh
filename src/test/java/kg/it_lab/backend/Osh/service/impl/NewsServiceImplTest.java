package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.news.NewsDetailResponse;
import kg.it_lab.backend.Osh.dto.news.NewsResponse;
import kg.it_lab.backend.Osh.entities.News;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.NewsMapper;
import kg.it_lab.backend.Osh.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    @InjectMocks
    private NewsServiceImpl newsService;

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsMapper newsMapper;

    private final News news = new News();
    private final NewsResponse newsResponse = new NewsResponse();
    private final NewsDetailResponse newsDetailResponse = new NewsDetailResponse();
    @Test
    void all() {
        when(newsRepository.findAll()).thenReturn(List.of(news));
        when(newsMapper.toDtos(anyList())).thenReturn(List.of(newsResponse));

        List<NewsResponse> responseList = newsService.all();

        assertNotNull(responseList);
        assertFalse(responseList.isEmpty());
        assertEquals(newsResponse, responseList.get(0));

        verify(newsRepository).findAll();
        verify(newsMapper).toDtos(anyList());
    }

    @Test
    void detail() {
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(news));
        when(newsMapper.toDetailDto(any(News.class))).thenReturn(newsDetailResponse);

        NewsDetailResponse responseResult = newsService.detail(1L);

        assertNotNull(responseResult);
        assertEquals(newsDetailResponse, responseResult);

        verify(newsRepository).findById(anyLong());
        verify(newsMapper).toDetailDto(any(News.class));
    }

    @Test
    void findByIdThrowsNotFoundException() {
        when(newsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> newsService.detail(anyLong()));

        verify(newsRepository).findById(anyLong());
        verify(newsMapper, never()).toDetailDto(any(News.class));
    }
}