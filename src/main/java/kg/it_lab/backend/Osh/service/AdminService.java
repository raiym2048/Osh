package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.news.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.dto.news.admin.AdminRegisterRequest;

public interface AdminService {
    void add(NewsRequest newsRequest);

    void updateByName(String name, NewsRequest newsRequest);

    void deleteByName(String name);
    void addEvent(EventRequest eventRequest);
    void updateEvent(String name , EventRequest eventRequest);
    void deleteEvent(String name);

}
