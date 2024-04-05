package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.image.ImageResponse;
import kg.it_lab.backend.Osh.entities.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ImageMapperImplTest {

    @InjectMocks
    private ImageMapperImpl imageMapper;

    private final Image image = new Image();

    @BeforeEach
    void setUp() {
        image.setId(1L);
        image.setName("name");
        image.setPath("path");
    }

    @Test
    void toDto() {
        ImageResponse responseResult = imageMapper.toDto(image);

        assertEquals(image.getName(), responseResult.getFileName());
        assertEquals(image.getPath(), responseResult.getPath());
    }

    @Test
    void toDtoS() {
        Image image1 = new Image();

        image1.setId(2L);
        image1.setName("name1");
        image1.setPath("path1");

        List<Image> imageList = new ArrayList<>();
        imageList.add(image);
        imageList.add(image1);

        List<ImageResponse> responseListResult = imageMapper.toDtoS(imageList);

        assertEquals(imageList.size(), responseListResult.size());
        assertEquals(imageList.get(0).getPath(), responseListResult.get(0).getPath());
        assertEquals(imageList.get(1).getName(), responseListResult.get(1).getFileName());
    }

    @Test
    void toDtoImage() {
        Image imageResult = imageMapper.toDtoImage(new Image(), image.getName());

        assertEquals(image.getName(), imageResult.getName());
    }
}