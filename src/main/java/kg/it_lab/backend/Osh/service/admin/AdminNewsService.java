package kg.it_lab.backend.Osh.service.admin;

import kg.it_lab.backend.Osh.dto.news.NewsRequest;

public interface AdminNewsService {
    void add(NewsRequest newsRequest, Long imageId);

    void updateById(Long id, NewsRequest newsRequest, Long imageId);

    void deleteById(Long id);
}
