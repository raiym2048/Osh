package kg.it_lab.backend.Osh.service.admin;

import kg.it_lab.backend.Osh.dto.hub.HubRequest;

public interface AdminHubService {
    void addHub(HubRequest hubRequest);
    void updateHub(Long id , HubRequest hubRequest);
    void deleteHub(Long id);
    void attachImageToHub(Long hubId, Long imageId);
}
