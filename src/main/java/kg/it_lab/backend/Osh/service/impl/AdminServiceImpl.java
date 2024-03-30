package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.entities.News;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.NewsMapper;
import kg.it_lab.backend.Osh.repository.NewsRepository;
import kg.it_lab.backend.Osh.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    @Override
    public void add(NewsRequest newsRequest) {
        if(newsRepository.findByName(newsRequest.getName()).isPresent()){
            throw new BadCredentialsException("News with name "+ newsRequest.getName() +" already exist!");
        }
        News news = new News();
        newsRepository.save(newsMapper.toDtoNews(news,newsRequest));
    }

    @Override
    public void updateByName(String name, NewsRequest newsRequest) {
        Optional<News> news =newsRepository.findByName(name);
        checker(news , name);
        newsRepository.save(newsMapper.toDtoNews(news.get() , newsRequest));
    }

    @Override
    public void deleteByName(String name) {
        Optional<News> news =newsRepository.findByName(name);
        checker(news , name);
        newsRepository.deleteByName(name);
    }


    private void checker(Optional<News> news, String name) {
        if(news.isEmpty()) {
            throw new NotFoundException("Product with name \"" + name + "\" not found", HttpStatus.NOT_FOUND);
        }
    }
}
