package kg.it_lab.backend.Osh.service.impl;
import kg.it_lab.backend.Osh.dto.news.NewsDetailResponse;
import kg.it_lab.backend.Osh.dto.news.NewsResponse;
import kg.it_lab.backend.Osh.entities.Event;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.News;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.NewsMapper;
import kg.it_lab.backend.Osh.repository.ImageRepository;
import kg.it_lab.backend.Osh.repository.NewsRepository;
import kg.it_lab.backend.Osh.service.NewsService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsMapper newsMapper;
    private final NewsRepository newsRepository;
    private final ImageRepository imageRepository;

    @Override
    public List<NewsResponse> all() {
        return newsMapper.toDtos(newsRepository.findAll());
    }

    @Override
    public NewsDetailResponse detail(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if(news.isEmpty())
            throw new NotFoundException("News with id: " + id + " - not found!", HttpStatus.NOT_FOUND);
        return newsMapper.toDetailDto(news.get());
    }

}
