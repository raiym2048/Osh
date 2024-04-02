package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.service.ServiceRequest;
import kg.it_lab.backend.Osh.dto.service.ServiceResponse;
import kg.it_lab.backend.Osh.entities.Service;
import kg.it_lab.backend.Osh.mapper.ServiceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public ServiceResponse toDto(Service service) {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setName(service.getName());
        serviceResponse.setDescription(service.getDescription());
        serviceResponse.setSubtopic(service.getSubtopic());
        return serviceResponse;
    }
    @Override
    public List<ServiceResponse> toDtos(List<Service> services) {
        List<ServiceResponse> serviceResponses = new ArrayList<>();
        for(Service service : services){
            serviceResponses.add(toDto(service));
        }
        return serviceResponses;
    }

    @Override
    public Service toDtoService(Service service, ServiceRequest serviceRequest) {
        service.setName(serviceRequest.getName());
        service.setDescription(serviceRequest.getDescription());
        service.setSubtopic(serviceRequest.getSubtopic());
        return service;
    }
}
