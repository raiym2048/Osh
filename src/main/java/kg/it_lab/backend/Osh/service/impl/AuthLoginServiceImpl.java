package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.config.JwtService;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginRequest;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginResponse;
import kg.it_lab.backend.Osh.entities.User;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.UserMapper;
import kg.it_lab.backend.Osh.repository.UserRepository;
import kg.it_lab.backend.Osh.service.AuthLoginService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthLoginServiceImpl implements AuthLoginService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthLoginResponse register(AuthLoginRequest authLoginRequest) {
        if(userRepository.findByEmail(authLoginRequest.getEmail()).isPresent()) {
            throw new BadCredentialsException("User with email: " + authLoginRequest.getEmail() + " is already exist");
        }
        User user = new User();
        user.setEmail(authLoginRequest.getEmail());
        user.setPassword(passwordEncoder.encode(authLoginRequest.getPassword()));
         // todo change here and role
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        AuthLoginResponse authLoginResponse = new AuthLoginResponse();
        authLoginResponse.setToken(token);
        return authLoginResponse;
    }


    @Override
    public AuthLoginResponse login(AuthLoginRequest authLoginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authLoginRequest.getEmail(),
                        authLoginRequest.getPassword()
                )
        );
        Optional<User> user = userRepository.findByEmail(authLoginRequest.getEmail());
        if(user.isEmpty()) {
            throw new NotFoundException("User with email \"" + authLoginRequest.getEmail() + "\" not found", HttpStatus.NOT_FOUND);
        }
        String token = jwtService.generateToken(user.get());
        AuthLoginResponse authLoginResponse = new AuthLoginResponse();
        authLoginResponse.setToken(token);
        return authLoginResponse;
    }
    @Override
    public User getUserFromToken(String token){

        String[] chunks = token.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        if (chunks.length != 3)
            throw new BadCredentialsException("Wrong token!");
        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;
        try {
            byte[] decodedBytes = decoder.decode(chunks[1]);
            object = (JSONObject) jsonParser.parse(decodedBytes);
        } catch (ParseException e) {
            throw new BadCredentialsException("Wrong token!!");
        }
        return userRepository.findByEmail(String.valueOf(object.get("sub"))).orElseThrow(() ->
                new BadCredentialsException("Wrong token!!!"));
    }
}
