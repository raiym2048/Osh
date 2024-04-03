package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Category;
import kg.it_lab.backend.Osh.entities.News;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NewsRepositoryTest {

    @Autowired
    private NewsRepository newsRepository;

    private final News news = new News();
    @BeforeEach
    void setUp() {
        news.setName("example");
        news.setDescription("It's example");
        news.setCreatedAt(LocalDateTime.now());
        news.setSlogan("It's slogan");
        news.setCategory(null);
        news.setImage(null);
        newsRepository.save(news);
    }

    @AfterEach
    void tearDown() {
        newsRepository.deleteAll();
    }

    @Test
    void itShouldFindNewsByName() {
        News newsFindByName = newsRepository.findByName(news.getName()).orElse(null);

        assertNotNull(newsFindByName);
        assertEquals(news.getName(), newsFindByName.getName());
    }

    @Test
    void itShouldDeleteNewsByName() {
        newsRepository.deleteByName(news.getName());

        News deletedNews = newsRepository.findByName(news.getName()).orElse(null);

        assertNull(deletedNews);
    }
}