package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.user.UserResponse;
import kg.it_lab.backend.Osh.service.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final MyUserDetailsService myUserDetailsService;

    @GetMapping("/getByEmail")
    public UserResponse getByEmail(String email) {
        return myUserDetailsService.getByEmail(email);
    }
}
