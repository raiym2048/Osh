package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.news.NewsRequest;

public interface AdminService {
    void add(NewsRequest newsRequest);

    void updateByName(String name, NewsRequest newsRequest);

    void deleteByName(String name);
}
