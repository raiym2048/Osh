package kg.it_lab.backend.Osh.service.admin;

import kg.it_lab.backend.Osh.dto.service.ServicesRequest;

public interface AdminServicesService {
    void addService(ServicesRequest servicesRequest);

    void updateService(Long id, ServicesRequest servicesRequest);

    void deleteService(Long id);

    void attachImageToService(Long serviceId, Long imageId);
}
