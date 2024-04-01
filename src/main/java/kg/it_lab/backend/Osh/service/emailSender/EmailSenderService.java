package kg.it_lab.backend.Osh.service.emailSender;

import kg.it_lab.backend.Osh.entities.User;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.repository.UserRepository;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailSenderService {
    private final GeneratorService generatorService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    @Autowired
    private JavaMailSender mailSender;
    public void sendPassword(String to ){
        try{

            Optional<User> user = userRepository.findByEmail(to);
            String username = generatorService.generateLogin();
            String password = generatorService.generatePassword();
            user.get().setUsername(username);
            user.get().setPassword(encoder.encode(password));
            userRepository.save(user.get());
            String email = to;
            if (email == null)
                throw new BadCredentialsException("Please, write your email");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ecomarket1111@gmail.com");
            message.setTo(to);
            message.setText("This is your new login: " + username+ " \n\nThis is your new password  " + password);
            message.setSubject("Youth of Osh. Date for auth.");
            mailSender.send(message);
        }catch (MailException e ){
            throw new BadRequestException("Email is invalid , please enter correct email");
        }

    }

}
