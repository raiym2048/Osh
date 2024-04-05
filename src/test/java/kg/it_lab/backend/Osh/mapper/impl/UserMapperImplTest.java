package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.user.UserRequest;
import kg.it_lab.backend.Osh.dto.user.UserResponse;
import kg.it_lab.backend.Osh.entities.Role;
import kg.it_lab.backend.Osh.entities.User;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperImplTest {

    @InjectMocks
    private UserMapperImpl userMapper;

    @Mock
    private RoleRepository roleRepository;

    private final Role role = new Role();
    @BeforeEach
    void setUp() {
        role.setName("ADMIN");
        roleRepository.save(role);
    }

    @Test
    void itShouldReturnResponseToDto() {
        User user = new User();
        user.setId(1L);
        user.setEmail("a@a");
        user.setUsername("example");
        user.setPassword("example");
        user.setRole(role);

        UserResponse expectResponse = new UserResponse();
        expectResponse.setId(1L);
        expectResponse.setEmail("a@a");
        expectResponse.setPassword("example");
        expectResponse.setRole("ADMIN");

        UserResponse exactResponse = userMapper.toDto(user);

        assertEquals(expectResponse, exactResponse);
        assertEquals(expectResponse.getId(), exactResponse.getId());
        assertEquals(expectResponse.getRole(), exactResponse.getRole());
    }

    @Test
    void itShoutReturnListOfUserResponseToDtoS() {
        User user = new User();
        user.setId(1L);
        user.setEmail("a@a");
        user.setUsername("example");
        user.setPassword("example");
        user.setRole(role);

        User user1 = new User();
        user1.setId(2L);
        user1.setEmail("b@b");
        user1.setPassword("example");
        user1.setRole(role);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setEmail("a@a");
        userResponse.setPassword("example");
        userResponse.setRole("ADMIN");

        UserResponse userResponse1 = new UserResponse();
        userResponse1.setId(2L);
        userResponse1.setEmail("b@b");
        userResponse1.setPassword("example");
        userResponse1.setRole("ADMIN");

        List<UserResponse> expectUserResponse = new ArrayList<>();
        expectUserResponse.add(userResponse);
        expectUserResponse.add(userResponse1);

        List<UserResponse> exactResponse = userMapper.toDtoS(userList);

        assertEquals(expectUserResponse, exactResponse);
        assertEquals(expectUserResponse.get(1), exactResponse.get(1));
        assertEquals(expectUserResponse.get(1).getRole(), exactResponse.get(1).getRole());
    }

    @Test
    void toDtoUserWhenRoleExists() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("a@a");
        userRequest.setPassword("a");
        userRequest.setRole("ADMIN");

        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(role));

        User resultUser = userMapper.toDtoUser(new User(), userRequest);

        assertNotNull(resultUser);
        assertEquals(userRequest.getEmail(), resultUser.getEmail());
        assertEquals(userRequest.getPassword(), resultUser.getPassword());
        assertEquals("ADMIN", resultUser.getRole().getName());
    }

    @Test
    void toDtoUserThrowsExceptionWhenRoleNotFound() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("user@example.com");
        userRequest.setPassword("password");
        userRequest.setRole("NON_EXISTENT_ROLE");

        when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userMapper.toDtoUser(new User(), userRequest));
    }
}