package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.auth.AuthLoginResponse;
import kg.it_lab.backend.Osh.dto.auth.EditorPasswordRequest;
import kg.it_lab.backend.Osh.dto.auth.MyData;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.news.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.dto.news.admin.AdminRegisterRequest;

public interface EditorService {
    void updateByName(String name, NewsRequest newsRequest);
    void registerEditor(AdminRegisterRequest adminRegisterRequest);
    AuthLoginResponse loginEditor(AdminLoginRequest adminLoginRequest);
    void changePassword(EditorPasswordRequest editorPasswordRequest);
}
