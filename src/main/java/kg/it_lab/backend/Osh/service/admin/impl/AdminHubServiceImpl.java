package kg.it_lab.backend.Osh.service.admin.impl;

import kg.it_lab.backend.Osh.dto.hub.HubRequest;
import kg.it_lab.backend.Osh.entities.Hub;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.mapper.HubMapper;
import kg.it_lab.backend.Osh.repository.HubRepository;
import kg.it_lab.backend.Osh.repository.ImageRepository;
import kg.it_lab.backend.Osh.service.ImageService;
import kg.it_lab.backend.Osh.service.admin.AdminHubService;
import kg.it_lab.backend.Osh.service.admin.AdminService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminHubServiceImpl implements AdminHubService {
    private final ImageRepository imageRepository;
    private final HubRepository hubRepository;
    private final HubMapper hubMapper;
    private final AdminService adminService;
    private final ImageService imageService;
    @Override
    public void addHub(HubRequest hubRequest) {
        if(hubRequest.getName().isEmpty() || hubRequest.getDescription().isEmpty()){
            throw new BadRequestException("Fields can't be empty");
        }
        if(hubRepository.findByName(hubRequest.getName()).isPresent()){
            throw new BadRequestException("Hub with name: " + hubRequest.getName() + " - already exist!");
        }
        Hub hub = new Hub();
        hubRepository.save(hubMapper.toDtoHub(hub, hubRequest));
    }

    @Override
    public void updateHub(Long id, HubRequest hubRequest) {
        Optional<Hub> hub = hubRepository.findById(id);
        if(hubRequest.getName().isEmpty() || hubRequest.getDescription().isEmpty()){
            throw new BadRequestException("Fields can't be empty");
        }
        if(hub.isEmpty()){
            throw new BadRequestException("Hub with this id wasn't found");
        }
        if (hubRepository.findByName(hubRequest.getName()).isPresent()) {
            throw new BadRequestException("Hub with name: " + hubRequest.getName() + " - already exist!");
        }
        hubRepository.save(hubMapper.toDtoHub(hub.get(), hubRequest));
    }

    @Override
    public void deleteHub(Long id) {
        Optional<Hub>hub = hubRepository.findById(id);
        if(hub.isEmpty()){
            throw new BadRequestException("Hub with this id wasn't found");
        }
        ArrayList<Long> ans = new ArrayList<>();
        if(hub.get().getImages() != null)
            for(Image image: hub.get().getImages())
                if(adminService.imageChecker(image) == 1)
                    ans.add(image.getId());

        hubRepository.deleteById(id);
        for (Long a : ans)
            imageService.deleteFile(a);
    }

    @Override
    public void attachImageToHub(Long hubId, Long imageId) {
        Optional<Hub> hub = hubRepository.findById(hubId);
        Optional<Image> image = imageRepository.findById(imageId);
        if(hub.isEmpty() || image.isEmpty()){
            throw new BadRequestException("Hub or image with this id wasn't found");
        }
        if(adminService.imageChecker(image.get()) > 0)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        List<Image> images = new ArrayList<>();
        if(hub.get().getImages() != null) images = hub.get().getImages();
        images.add(image.get());
        hub.get().setImages(images);
        hubRepository.save(hub.get());
    }

}
