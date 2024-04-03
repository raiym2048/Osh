package kg.it_lab.backend.Osh.mapper;
import kg.it_lab.backend.Osh.dto.service.ServiceRequest;
import kg.it_lab.backend.Osh.dto.service.ServiceResponse;
import kg.it_lab.backend.Osh.entities.Services;

import java.util.List;

public interface ServiceMapper {
    ServiceResponse toDto(Services services);
    List<ServiceResponse> toDtos(List<Services> services);
    Services toDtoService(Services services, ServiceRequest serviceRequest);


}
