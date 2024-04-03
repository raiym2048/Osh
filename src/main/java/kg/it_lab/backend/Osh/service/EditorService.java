package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginResponse;
import kg.it_lab.backend.Osh.dto.auth.EditorPasswordRequest;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.service.ServicesRequest;

public interface EditorService {
    void updateById(Long id, NewsRequest newsRequest , Long imageId);
    void updateProject(Long id, ProjectRequest projectRequest );
    void updateService(Long id, ServicesRequest servicesRequest);
    AuthLoginResponse loginEditor(AdminLoginRequest adminLoginRequest);
    void changePassword(String token ,EditorPasswordRequest editorPasswordRequest);
    void updateActivity(Long id , ActivityRequest activityRequest , Long imageId );
    void updateEvent(Long eventId , EventRequest eventRequest , Long imageId );
}
