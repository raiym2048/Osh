package kg.it_lab.backend.Osh.service.admin.impl;

import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.News;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.*;
import kg.it_lab.backend.Osh.repository.*;
import kg.it_lab.backend.Osh.service.ImageService;
import kg.it_lab.backend.Osh.service.admin.AdminNewsService;
import kg.it_lab.backend.Osh.service.admin.AdminService;
import kg.it_lab.backend.Osh.service.emailSender.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminNewsServiceImpl implements AdminNewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final AdminService adminService;

    @Override
    public void add(NewsRequest newsRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if(adminService.imageChecker(image.get()) > 0)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        if (newsRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the news can't be empty");
        }
        if(categoryRepository.findById(newsRequest.getCategoryId()).isEmpty()){
            throw new NotFoundException("Category with this id "+ newsRequest.getCategoryId()+" not found" , HttpStatus.NOT_FOUND);
        }
        if (newsRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the news can't be empty");
        }
        News news = new News();
        newsRepository.save(newsMapper.toDtoNews(news, newsRequest, image.get()));
    }

    @Override
    public void updateById(Long id, NewsRequest newsRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if((adminService.imageChecker(image.get()) == 1 && !newsRepository.existsByImage(image.get())) || adminService.imageChecker(image.get()) > 1)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        Optional<News> news = newsRepository.findById(id);
        if (newsRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the news can't be empty");
        }
        if (newsRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the news can't be empty");
        }
        if (newsRequest.getCategoryId() == null)
            throw new BadRequestException("Category of the news can't be empty");

        if (categoryRepository.findById(newsRequest.getCategoryId()).isEmpty())
            throw new NotFoundException("Category with id: " + newsRequest.getCategoryId() + " - wasn't found!!", HttpStatus.NOT_FOUND);

        adminService.checker(news, id);
        news.get().setImage(null);
        newsRepository.save(newsMapper.toDtoNews(news.get(), newsRequest, image.get()));
    }

    @Override
    public void deleteById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        adminService.checker(news, id);
        int cnt = 0;
        if(news.get().getImage() != null){
            cnt = adminService.imageChecker(news.get().getImage());
            System.out.println(cnt);
        }

        newsRepository.deleteById(id);
        if(cnt == 1)imageService.deleteFile(news.get().getImage().getId());

//        imageRepository.deleteByName(news.get().getImage().getName());
//        eventRepository.update

    }
}