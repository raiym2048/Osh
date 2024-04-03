package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.service.ServicesRequest;
import kg.it_lab.backend.Osh.dto.service.ServicesResponse;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.Services;
import kg.it_lab.backend.Osh.mapper.ServicesMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ServicesMapperImpl implements ServicesMapper {

    @Override
    public ServicesResponse toDto(Services services) {
        ServicesResponse servicesResponse = new ServicesResponse();
        servicesResponse.setName(services.getName());
        servicesResponse.setDescription(services.getDescription());
        servicesResponse.setSubtopic(services.getSubtopic());
        ArrayList<String> paths = new ArrayList<>();
        for(Image image: services.getImages()){
            paths.add(image.getPath());
        }
        servicesResponse.setImagePaths(paths);
        return servicesResponse;
    }
    @Override
    public List<ServicesResponse> toDtoS(List<Services> services) {
        List<ServicesResponse> servicesResponse = new ArrayList<>();
        for(Services service : services){
            servicesResponse.add(toDto(service));
        }
        return servicesResponse;
    }

    @Override
    public Services toDtoService(Services services, ServicesRequest servicesRequest) {
        services.setName(servicesRequest.getName());
        services.setDescription(servicesRequest.getDescription());
        services.setSubtopic(servicesRequest.getSubtopic());
        return services;
    }
}
