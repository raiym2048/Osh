package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.news.NewsRequest;

public interface EditorService {
    void updateByName(String name, NewsRequest newsRequest);
}
