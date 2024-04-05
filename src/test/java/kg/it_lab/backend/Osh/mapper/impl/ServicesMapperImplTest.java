package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.service.ServicesRequest;
import kg.it_lab.backend.Osh.dto.service.ServicesResponse;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServicesMapperImplTest {

    @InjectMocks
    private ServicesMapperImpl servicesMapper;

    private final Services services = new Services();
    private final List<Image> imageList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        Image image = new Image();
        image.setId(1L);
        image.setName("name");
        image.setPath("path");

        Image image1 = new Image();
        image1.setId(2L);
        image1.setName("name1");
        image1.setPath("path1");

        imageList.add(image);
        imageList.add(image1);

        services.setId(1L);
        services.setName("name");
        services.setSubtopic("subtopic");
        services.setDescription("description");
        services.setImages(imageList);
    }

    @Test
    void toDto() {
        ServicesResponse responseResult = servicesMapper.toDto(services);

        assertEquals(services.getId(), responseResult.getId());
        assertEquals(services.getImages().size(), responseResult.getImagePaths().size());
        assertEquals(services.getImages().get(0).getPath(), responseResult.getImagePaths().get(0));
    }

    @Test
    void toDtoS() {
        Services services1 = new Services();
        services1.setId(2L);
        services1.setName("name1");
        services1.setSubtopic("subtopic1");
        services1.setDescription("description1");
        services1.setImages(imageList);

        List<Services> servicesList = new ArrayList<>();
        servicesList.add(services);
        servicesList.add(services1);

        List<ServicesResponse> responseListResult = servicesMapper.toDtoS(servicesList);

        assertEquals(servicesList.size(), responseListResult.size());
        assertEquals(servicesList.get(0).getId(), responseListResult.get(0).getId());
        assertEquals(servicesList.get(1).getImages().get(0).getPath(), responseListResult.get(1).getImagePaths().get(0));
    }

    @Test
    void toDtoService() {
        ServicesRequest servicesRequest = new ServicesRequest();
        servicesRequest.setName("name");
        servicesRequest.setSubtopic("subtopic");
        servicesRequest.setDescription("description");

        Services servicesResult = servicesMapper.toDtoService(new Services(), servicesRequest);

        assertEquals(services.getName(), servicesResult.getName());
        assertEquals(services.getSubtopic(), servicesRequest.getSubtopic());
        assertEquals(services.getDescription(), servicesRequest.getDescription());
    }
}