package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.user.UserResponse;
import kg.it_lab.backend.Osh.entities.User;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.UserMapper;
import kg.it_lab.backend.Osh.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MyUserDetailsServiceImplTest {

    @InjectMocks
    private MyUserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    private final User user = new User();
    private final UserResponse userResponse = new UserResponse();


    @Test
    void getByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userResponse);

        UserResponse responseResult = userDetailsService.getByEmail(anyString());

        assertNotNull(responseResult);
        assertEquals(userResponse, responseResult);

        verify(userRepository).findByEmail(anyString());
        verify(userMapper).toDto(any(User.class));
    }

    @Test
    void findByEmailThrowsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userDetailsService.getByEmail(anyString()));

        verify(userRepository).findByEmail(anyString());
        verify(userMapper, never()).toDto(any(User.class));
    }
}