package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private final Category category = new Category();

    @BeforeEach
    void setUp() {
        category.setName("Category");
        category.setEvents(null);
        category.setNews(null);
        categoryRepository.save(category);
    }

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    void itShouldFindCategoryByName() {
        Category findCategoryByName = categoryRepository.findByName(category.getName()).orElse(null);

        assertNotNull(findCategoryByName);
        assertEquals(category.getName(), findCategoryByName.getName());
    }
}