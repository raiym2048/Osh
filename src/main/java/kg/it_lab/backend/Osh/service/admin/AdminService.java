package kg.it_lab.backend.Osh.service.admin;
import kg.it_lab.backend.Osh.dto.admin.EditorRegisterRequest;
import kg.it_lab.backend.Osh.dto.role.RoleRequest;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.News;

import java.util.Optional;

public interface AdminService {
    void registerEditor(EditorRegisterRequest editorRegisterRequest);
    void addRole(RoleRequest roleRequest);
    void deleteRole(Long id );
    void checker(Optional<News> news, Long id);
    int imageChecker(Image image);

}
