package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.news.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.dto.news.admin.AdminRegisterRequest;
import kg.it_lab.backend.Osh.entities.Event;
import kg.it_lab.backend.Osh.entities.News;
import kg.it_lab.backend.Osh.entities.User;
import kg.it_lab.backend.Osh.enums.Role;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.EventMapper;
import kg.it_lab.backend.Osh.mapper.NewsMapper;
import kg.it_lab.backend.Osh.repository.EventRepository;
import kg.it_lab.backend.Osh.repository.NewsRepository;
import kg.it_lab.backend.Osh.repository.UserRepository;
import kg.it_lab.backend.Osh.service.AdminService;
import kg.it_lab.backend.Osh.service.emailSender.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    @Override
    public void add(NewsRequest newsRequest) {
        if(newsRequest.getName().isEmpty()){
            throw new BadRequestException("Title of the news can't be empty");
        }
        if(newsRequest.getDescription().isEmpty()){
            throw new BadRequestException("Content of the news can't be empty");
        }
        if(newsRepository.findByName(newsRequest.getName()).isPresent()){
            throw new BadCredentialsException("News with name "+ newsRequest.getName() +" already exist!");
        }
        News news = new News();
        newsRepository.save(newsMapper.toDtoNews(news,newsRequest));
    }

    @Override
    public void updateByName(String name, NewsRequest newsRequest) {
        Optional<News> news =newsRepository.findByName(name);
        if(newsRequest.getName().isEmpty()){
            throw new BadRequestException("Title of the news can't be empty");
        }
        if(newsRequest.getDescription().isEmpty()){
            throw new BadRequestException("Content of the news can't be empty");
        }
        if(news.isEmpty()){
            throw new NotFoundException("Title of news with this name wasn't found" ,HttpStatus.NOT_FOUND);
        }
        if(newsRepository.findByName(newsRequest.getName()).isPresent()){
            throw new BadRequestException("Title of news with this name already exist");
        }
        checker(news , name);
        newsRepository.save(newsMapper.toDtoNews(news.get() , newsRequest));
    }

    @Override
    public void deleteByName(String name) {
        Optional<News> news =newsRepository.findByName(name);
        checker(news , name);
        newsRepository.deleteByName(name);
    }

    @Override
    public void addEvent(EventRequest eventRequest) {
        if(eventRequest.getName().isEmpty()){
            throw new BadRequestException("Title of the event can't be empty");
        }
        if(eventRequest.getDescription().isEmpty()){
            throw new BadRequestException("Content of the event can't be empty");
        }
        if(eventRequest.getCategory().isEmpty()){
            throw new BadRequestException("Category of event can't be empty ");
        }
        if(eventRequest.getSlogan().isEmpty()){
            throw new BadRequestException("Slogan of event can't be empty ");
        }
        if(eventRepository.findByName(eventRequest.getName()).isPresent()){
            throw new BadCredentialsException("Event with name "+ eventRequest.getName() +" already exist!");
        }
        Event event = new Event();
        eventRepository.save(eventMapper.toDtoEvent(event,eventRequest));
    }

    @Override
    public void updateEvent(String name, EventRequest eventRequest) {
        Optional<Event> event = eventRepository.findByName(name);
        if(eventRequest.getName().isEmpty()){
            throw new BadRequestException("Title of the event can't be empty");
        }
        if(eventRequest.getDescription().isEmpty()){
            throw new BadRequestException("Content of the event can't be empty");
        }
        if(event.isEmpty()){
            throw new NotFoundException("Title of event with this name wasn't found" ,HttpStatus.NOT_FOUND);
        }
        if(eventRepository.findByName(eventRequest.getName()).isPresent()){
            throw new BadRequestException("Title of event with this name already exist");
        }
        if(eventRequest.getCategory().isEmpty()){
            throw new BadRequestException("Category of event can't be empty ");
        }
        if(eventRequest.getSlogan().isEmpty()){
            throw new BadRequestException("Slogan of event can't be empty ");
        }
        eventRepository.save(eventMapper.toDtoEvent(event.get() , eventRequest));


    }

    @Override
    public void deleteEvent(String name) {
        Optional<Event> event = eventRepository.findByName(name);
        if(event.isEmpty()){
            throw new NotFoundException("Event with name \"" + name + "\" not found", HttpStatus.NOT_FOUND);
        }
        eventRepository.deleteByName(name);
    }


    private void checker(Optional<News> news, String name) {
        if(news.isEmpty()) {
            throw new NotFoundException("News with name \"" + name + "\" not found", HttpStatus.NOT_FOUND);
        }
    }
}
