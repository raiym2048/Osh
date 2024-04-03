package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.auth.MyData;
import kg.it_lab.backend.Osh.dto.image.ImageResponse;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.repository.ImageRepository;
import kg.it_lab.backend.Osh.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @GetMapping("/all")
    public List<ImageResponse> all(){
        return imageService.all();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<InputStreamResource> viewFile(@PathVariable Long id) {
        var s3Object = imageService.getFile(id);
        var content = s3Object.getObjectContent();
        String fileName = imageRepository.findById(id).get().getName();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // This content type can change by your file :)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+fileName+"\"")
                .body(new InputStreamResource(content));
    }

    @PostMapping("/uploadFile")
    public MyData uploadFile(@RequestParam(value = "file") MultipartFile file) {
        String fileName = imageService.uploadFile(file);
        MyData data = new MyData();
        data.setMessage("File: " + fileName + " - added successfully!");
        return data;
    }

    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long id) {
        byte[] data = imageService.downloadFile(id);
        ByteArrayResource resource = new ByteArrayResource(data);
        String fileName = imageRepository.findById(id).get().getName();
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment: filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/deleteFile/{id}")
    public MyData deleteFile(@PathVariable Long id) {
        String fileName = imageService.getName(id);
        imageService.deleteFile(id);
        MyData data = new MyData();
        data.setMessage("File: " + fileName + " - deleted successfully!");
        return data;
    }
}
