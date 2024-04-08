package kg.it_lab.backend.Osh.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import kg.it_lab.backend.Osh.dto.image.ImageResponse;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.mapper.ImageMapper;
import kg.it_lab.backend.Osh.repository.ImageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {
    @InjectMocks
    private ImageServiceImpl imageService;

    @Mock
    private ImageMapper imageMapper;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private AmazonS3 s3Client;

    private final Image image = new Image();
    private final ImageResponse imageResponse = new ImageResponse();
    private final String bucketName = "test_Name";
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void all() {
        when(imageRepository.findAll()).thenReturn(List.of(image));
        when(imageMapper.toDtoS(List.of(image))).thenReturn(List.of(imageResponse));

        List<ImageResponse> responseListResult = imageService.all();

        assertNotNull(responseListResult);
        assertFalse(responseListResult.isEmpty());
        assertEquals(imageResponse, responseListResult.get(0));

        verify(imageRepository).findAll();
        verify(imageMapper).toDtoS(List.of(image));
    }

//    @Test
//    void getFile() {
//        Long id = 1L; // Example ID
//        String expectedFileName = "test-file.jpg"; // Assume this is the file name associated with the ID
//        S3Object expectedS3Object = new S3Object(); // Mocked S3 object returned by the client
//
//        when(imageRepository.findById(id)).thenReturn(Optional.of(image)); // Mocking the repository response
//        when(s3Client.getObject(anyString(), eq(expectedFileName))).thenReturn(expectedS3Object); // Mocking the S3 client response
//
//        // Act
//        S3Object result = imageService.getFile(id);
//
//        // Assert
//        verify(imageRepository, times(1)).findById(id); // Verify that findById was called exactly once
//        verify(s3Client, times(1)).getObject(anyString(), eq(expectedFileName)); // Verify that getObject was called exactly once with the expected file name
//        assertEquals(expectedS3Object, result); // Assert that the returned S3Object is the one we mocked
//    }

    @Test
    void uploadFile() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "test content".getBytes());

        Image dummyImage = new Image();
        when(imageRepository.saveAndFlush(any(Image.class))).thenReturn(dummyImage);
        when(imageMapper.toDtoImage(any(Image.class), anyString())).thenReturn(dummyImage);

        String fileName = imageService.uploadFile(multipartFile);

        verify(s3Client).putObject(any(PutObjectRequest.class));
        assertNotNull(fileName);
    }

    @Test
    void downloadFile() {
        Long id = 1L;
        String expectedFileName = "file_" + id + ".txt";
        byte[] expectedContent = "Hello, world!".getBytes();

        S3Object mockS3Object = new S3Object();
        mockS3Object.setObjectContent(new S3ObjectInputStream(new ByteArrayInputStream(expectedContent), null));

        when(s3Client.getObject(bucketName, expectedFileName)).thenReturn(mockS3Object);
        when(imageRepository.findById(id)).thenReturn(Optional.of(new Image()));

        byte[] content = imageService.downloadFile(id);

        assertArrayEquals(expectedContent, content, "The content of the downloaded file should match the expected content.");
    }

//    @Test
//    void deleteFile() {
//        Long id = 1L;
//        String fileName = "file_" + id + ".txt";
//
//        when(imageRepository.findByName(fileName)).thenReturn(Optional.of(new Image()));
//
//        imageService.deleteFile(id);
//
//        verify(s3Client, times(1)).deleteObject(bucketName, fileName);
//
//        verify(imageRepository, times(1)).deleteByName(fileName);
//    }

    @Test
    void getName() {
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        String getImageName = imageService.getName(anyLong());


    }
}