package kg.it_lab.backend.Osh.mapper;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectResponse;
import kg.it_lab.backend.Osh.dto.service.ServiceRequest;
import kg.it_lab.backend.Osh.dto.service.ServiceResponse;
import kg.it_lab.backend.Osh.entities.Project;
import kg.it_lab.backend.Osh.entities.Service;

import java.util.List;

public interface ServiceMapper {
    ServiceResponse toDto(Service service);
    List<ServiceResponse> toDtos(List<Service> services);
    Service toDtoService(Service service , ServiceRequest serviceRequest);


}
