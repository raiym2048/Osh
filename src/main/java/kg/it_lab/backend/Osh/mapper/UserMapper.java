package kg.it_lab.backend.Osh.mapper;

import kg.it_lab.backend.Osh.dto.user.UserRequest;
import kg.it_lab.backend.Osh.dto.user.UserResponse;
import kg.it_lab.backend.Osh.entities.User;

import java.util.List;

public interface UserMapper {
    UserResponse toDto(User user);
    List<UserResponse> toDtoS(List<User> users);
    User toDtoUser(User user, UserRequest userRequest);
}
