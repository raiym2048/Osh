package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.user.UserRequest;
import kg.it_lab.backend.Osh.dto.user.UserResponse;
import kg.it_lab.backend.Osh.entities.User;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.UserMapper;
import kg.it_lab.backend.Osh.repository.UserRepository;
import kg.it_lab.backend.Osh.service.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UserResponse getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new NotFoundException("User with email: " + email + " not found", HttpStatus.NOT_FOUND);
        }
        return userMapper.toDto(user.get());
    }
}
