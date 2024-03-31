package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.user.UserResponse;


public interface MyUserDetailsService {
    UserResponse getByEmail(String email);
}
