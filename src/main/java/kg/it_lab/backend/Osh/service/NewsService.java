package kg.it_lab.backend.Osh.service;


import kg.it_lab.backend.Osh.dto.news.NewsDetailResponse;
import kg.it_lab.backend.Osh.dto.news.NewsResponse;

import java.util.List;

public interface NewsService {

    List<NewsResponse> all();

    NewsDetailResponse detail(Long id);
}
