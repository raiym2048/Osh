package kg.it_lab.backend.Osh.service;

import com.amazonaws.services.s3.model.S3Object;
import kg.it_lab.backend.Osh.dto.image.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<ImageResponse> all();

    S3Object getFile(Long id);

    String uploadFile(MultipartFile file);

    byte[] downloadFile(Long id);

    void deleteFile(Long id);

    String getName(Long id);
}
