package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.user.UserRequest;
import kg.it_lab.backend.Osh.dto.user.UserResponse;
import kg.it_lab.backend.Osh.entities.Role;
import kg.it_lab.backend.Osh.entities.User;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.UserMapper;
import kg.it_lab.backend.Osh.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final RoleRepository roleRepository;
    @Override
    public UserResponse toDto(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setRole(user.getRole().getName());
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

        Optional<Role> role = roleRepository.findByName(userRequest.getRole());
        if(role.isEmpty()) {
            throw new NotFoundException("Role with name \"" + userRequest.getRole() + "\" not found", HttpStatus.NOT_FOUND);
        }
        user.setRole(role.get());
        return user;
    }
}
