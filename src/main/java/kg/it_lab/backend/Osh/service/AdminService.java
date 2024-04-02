package kg.it_lab.backend.Osh.service;


import kg.it_lab.backend.Osh.dto.admin.EditorRegisterRequest;
import kg.it_lab.backend.Osh.dto.admin.category.CategoryRequest;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.role.RoleRequest;

public interface AdminService {
    void add(NewsRequest newsRequest , String  imageName);

    void updateByName(String name, NewsRequest newsRequest , String imageName);

    void deleteByName(String name);
    void addEvent(EventRequest eventRequest , String imageName );
    void updateEvent(String eventName , EventRequest eventRequest ,   String imageName );
    void deleteEvent(String name);
    void addCategory(CategoryRequest categoryRequest);
    void deleteCategory(String name);
    void registerEditor(EditorRegisterRequest editorRegisterRequest);
    void addRole(RoleRequest roleRequest);

}
