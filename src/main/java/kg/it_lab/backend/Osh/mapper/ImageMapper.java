package kg.it_lab.backend.Osh.mapper;

import kg.it_lab.backend.Osh.dto.image.ImageResponse;
import kg.it_lab.backend.Osh.entities.Image;

import java.util.List;

public interface ImageMapper {

    ImageResponse toDto(Image image);

    List<ImageResponse> toDtoS(List<Image> all);

    Image toDtoImage(Image image, String name);
}
