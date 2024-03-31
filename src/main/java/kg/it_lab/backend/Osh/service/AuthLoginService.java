package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.auth.AuthLoginRequest;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginResponse;
import kg.it_lab.backend.Osh.dto.user.UserRequest;

public interface AuthLoginService {
    AuthLoginResponse register(AuthLoginRequest authLoginRequest);
    AuthLoginResponse login(AuthLoginRequest authLoginRequest);
}
