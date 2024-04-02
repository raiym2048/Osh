package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.config.JwtService;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginResponse;
import kg.it_lab.backend.Osh.dto.auth.EditorPasswordRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.entities.News;
import kg.it_lab.backend.Osh.entities.User;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.NewsMapper;
import kg.it_lab.backend.Osh.repository.NewsRepository;
import kg.it_lab.backend.Osh.repository.RoleRepository;
import kg.it_lab.backend.Osh.repository.UserRepository;
import kg.it_lab.backend.Osh.service.EditorService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EditorServiceImpl implements EditorService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    @Override
    public void updateByName(String name, NewsRequest newsRequest , String  imageName) {
        Optional<News> news =newsRepository.findByName(name);
        if(newsRequest.getName().isEmpty()){
            throw new BadRequestException("Title of the news can't be empty");
        }
        if(newsRequest.getDescription().isEmpty()){
            throw new BadRequestException("Content of the news can't be empty");
        }
        if(news.isEmpty()){
            throw new NotFoundException("Title of news with this name wasn't found" ,HttpStatus.NOT_FOUND);
        }
        if(newsRepository.findByName(newsRequest.getName()).isPresent()){
            throw new BadRequestException("Title of news with this name already exist");
        }
        checker(news , name);
        newsRepository.save(newsMapper.toDtoNews(news.get() , newsRequest));
    }

    @Override
    public AuthLoginResponse loginEditor(AdminLoginRequest adminLoginRequest) {
        Optional<User>user =userRepository.findByEmail(adminLoginRequest.getEmail());
        if(adminLoginRequest.getPassword().isEmpty() || adminLoginRequest.getEmail().isEmpty()){
            throw new BadRequestException("Your password or email can't be empty");
        }
        if(user.isEmpty()){
            throw new NotFoundException("User with this username was not found" , HttpStatus.NOT_FOUND);
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            adminLoginRequest.getEmail(),
                            adminLoginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
        String token = jwtService.generateToken(user.get());
        AuthLoginResponse authLoginResponse = new AuthLoginResponse();
        authLoginResponse.setToken(token);
        return  authLoginResponse;

    }

    @Override
    public void changePassword(String token  ,EditorPasswordRequest editorPasswordRequest) {
        User editor  = getUserFromToken(token); //todo change here
        String pass1 = editorPasswordRequest.getPassword1();
        String pass2 = editorPasswordRequest.getPassword2();
        if(!Objects.equals(pass1, pass2)){
            throw new BadRequestException("Passwords don't match!");
        }
        editor.setPassword(pass1);
        userRepository.save(editor);

    }

    @Override
    public User getUserFromToken(String token) {
        String[] chunks = token.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        if (chunks.length != 3)
            throw new org.springframework.security.authentication.BadCredentialsException("Wrong token!");
        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;
        try {
            byte[] decodedBytes = decoder.decode(chunks[1]);
            object = (JSONObject) jsonParser.parse(decodedBytes);
        } catch (ParseException e) {
            throw new org.springframework.security.authentication.BadCredentialsException("Wrong token!!");
        }
        return userRepository.findByUsername(String.valueOf(object.get("sub"))).orElseThrow(() -> new org.springframework.security.authentication.BadCredentialsException("Wrong token!!!"));
    }

    private void checker(Optional<News> news, String name) {
        if(news.isEmpty()) {
            throw new NotFoundException("Product with name  " + name + " not found", HttpStatus.NOT_FOUND);
        }
    }


}
