package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.auth.AuthLoginResponse;
import kg.it_lab.backend.Osh.dto.auth.EditorPasswordRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.service.ServiceRequest;
import kg.it_lab.backend.Osh.entities.User;

public interface EditorService {
    void updateByName(String name, NewsRequest newsRequest , String  imageName);
    AuthLoginResponse loginEditor(AdminLoginRequest adminLoginRequest);
    void updateProjectEditor(String name, ProjectRequest projectRequest );
    void updateServiceEditor(String name, ServiceRequest serviceRequest );
    void changePassword(String token  ,EditorPasswordRequest editorPasswordRequest);
    public User getUserFromToken(String token);
}
