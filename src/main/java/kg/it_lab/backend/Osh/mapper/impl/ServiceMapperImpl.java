package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.service.ServiceRequest;
import kg.it_lab.backend.Osh.dto.service.ServiceResponse;
import kg.it_lab.backend.Osh.entities.Services;
import kg.it_lab.backend.Osh.mapper.ServiceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public ServiceResponse toDto(Services services) {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setName(services.getName());
        serviceResponse.setDescription(services.getDescription());
        serviceResponse.setSubtopic(services.getSubtopic());
        return serviceResponse;
    }
    @Override
    public List<ServiceResponse> toDtos(List<Services> services) {
        List<ServiceResponse> serviceResponses = new ArrayList<>();
        for(Services service : services){
            serviceResponses.add(toDto(service));
        }
        return serviceResponses;
    }

    @Override
    public Services toDtoService(Services services, ServiceRequest serviceRequest) {
        services.setName(serviceRequest.getName());
        services.setDescription(serviceRequest.getDescription());
        services.setSubtopic(serviceRequest.getSubtopic());
        return services;
    }
}
