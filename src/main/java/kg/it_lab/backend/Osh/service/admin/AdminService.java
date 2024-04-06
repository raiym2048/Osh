package kg.it_lab.backend.Osh.service.admin;


import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.admin.EditorRegisterRequest;
import kg.it_lab.backend.Osh.dto.admin.category.CategoryRequest;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.numbers.NumbersRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.role.RoleRequest;
import kg.it_lab.backend.Osh.dto.service.ServicesRequest;
import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.News;

import java.util.Optional;

public interface AdminService {
    void addCategory(CategoryRequest categoryRequest);
    void deleteCategory(Long id);
    void registerEditor(EditorRegisterRequest editorRegisterRequest);
    void addRole(RoleRequest roleRequest);
    void deleteRole(Long id );
    void checker(Optional<News> news, Long id);
    int imageChecker(Image image);
}
