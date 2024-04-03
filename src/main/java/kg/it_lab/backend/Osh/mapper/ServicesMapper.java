package kg.it_lab.backend.Osh.mapper;
import kg.it_lab.backend.Osh.dto.service.ServicesRequest;
import kg.it_lab.backend.Osh.dto.service.ServicesResponse;
import kg.it_lab.backend.Osh.entities.Services;

import java.util.List;

public interface ServicesMapper {
    ServicesResponse toDto(Services services);
    List<ServicesResponse> toDtoS(List<Services> services);
    Services toDtoService(Services services, ServicesRequest servicesRequest);


}
