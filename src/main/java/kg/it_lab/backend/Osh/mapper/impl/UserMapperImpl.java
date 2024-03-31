package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.user.UserRequest;
import kg.it_lab.backend.Osh.dto.user.UserResponse;
import kg.it_lab.backend.Osh.entities.User;
import kg.it_lab.backend.Osh.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserResponse toDto(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(userResponse.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setRole(userResponse.getRole());
        return userResponse;
    }

    @Override
    public List<UserResponse> toDtoS(List<User> users) {
        List<UserResponse> userResponses = new ArrayList<>();
        for(User user : users) {
            userResponses.add(toDto(user));
        }
        return userResponses;
    }

    @Override
    public User toDtoUser(User user, UserRequest userRequest) {
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        userRequest.setRole(userRequest.getRole());
        return user;
    }
}
