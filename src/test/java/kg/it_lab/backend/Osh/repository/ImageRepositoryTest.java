package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Image;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    private final Image image = new Image();

    @BeforeEach
    void setUp() {
        image.setName("1345342344353.gkenj.jpg");
        image.setPath("erignkre");
        imageRepository.save(image);
    }

    @AfterEach
    void tearDown() {
        imageRepository.deleteAll();
    }

    @Test
    void itShouldFindImageByName() {
        Image findImageByName = imageRepository.findByName(image.getName()).orElse(null);

        assertNotNull(findImageByName);
        assertEquals(image.getName(), findImageByName.getName());
    }

    @Test
    void deleteByName() {
        imageRepository.deleteByName(image.getName());

        Image emptyImage = imageRepository.findByName(image.getName()).orElse(null);

        assertNull(emptyImage);
    }

    // todo write also for checking the path of the image
}