package kg.it_lab.backend.Osh.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import kg.it_lab.backend.Osh.dto.image.ImageResponse;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.ImageMapper;
import kg.it_lab.backend.Osh.repository.ImageRepository;
import kg.it_lab.backend.Osh.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ImageServiceImpl implements ImageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;
    private final AmazonS3 s3Client;
    @Override
    public List<ImageResponse> all() {
        return imageMapper.toDtoS(imageRepository.findAll());
    }

    @Override
    public S3Object getFile(Long id) {
        String fileName = getName(id);
        return s3Client.getObject(bucketName, fileName);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        Image image = new Image();
        Image image1 = imageRepository.saveAndFlush(imageMapper.toDtoImage(image, fileName));
        image1.setPath("localhost:5151/image/view/" + image1.getId());
        imageRepository.save(image1);
        return fileName;
    }

    @Override
    public byte[] downloadFile(Long id) {
        String fileName = getName(id);
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(Long id) {
        String fileName = getName(id);
        if(imageRepository.findByName(fileName).isEmpty()) {
            throw new NotFoundException("File with name=\"" + fileName + "\" not found", HttpStatus.NOT_FOUND);
        }
        s3Client.deleteObject(bucketName, fileName);
        imageRepository.deleteByName(fileName);
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new BadCredentialsException("Error converting multiPartFile to file");
        }
        return convertedFile;
    }

    @Override
    public String getName(Long id){
        Optional<Image> image = imageRepository.findById(id);
        if(image.isEmpty())
            throw new NotFoundException("Image with id: " + id + " - not found", HttpStatus.NOT_FOUND);
        return image.get().getName();
    }
}
