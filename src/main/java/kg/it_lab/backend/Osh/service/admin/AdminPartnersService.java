package kg.it_lab.backend.Osh.service.admin;

public interface AdminPartnersService {
    void addPartners(Long imageId);
    void  updatePartners(Long id, Long imageId);
    void deletePartners(Long id);
}
