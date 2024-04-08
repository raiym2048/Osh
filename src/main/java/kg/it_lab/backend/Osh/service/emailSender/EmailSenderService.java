package kg.it_lab.backend.Osh.service.emailSender;

import kg.it_lab.backend.Osh.entities.User;
import kg.it_lab.backend.Osh.entities.Volunteer;
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

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailSenderService {
    private final GeneratorService generatorService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    @Autowired
    private JavaMailSender mailSender;

    public void sendMessageToAdmin(Volunteer volunteer) {
        String pathToAccept = "localhost:5151/admin/volunteer/accept/" + volunteer.getId();
        String pathToReject = "localhost:5151/admin/volunteer/reject/" + volunteer.getId(); // todo delete mapping
        List<User> userList = userRepository.findAll();
        String adminEmail = null;
        for(User admin : userList) {
            if(admin.getRole().getName().equalsIgnoreCase("admin")) {
                adminEmail = admin.getEmail();
            }
        }
        if(adminEmail == null) {
            throw new BadCredentialsException("We can't send message to Admin");
        }
        try {
            SimpleMailMessage messageToAdmin = getSimpleMailMessage(volunteer, adminEmail, pathToAccept, pathToReject);
            mailSender.send(messageToAdmin);
        } catch (MailException ex) {
            throw new BadCredentialsException("We can't send message to Admin");
        }
    }

    private static SimpleMailMessage getSimpleMailMessage(Volunteer volunteer, String adminEmail, String pathToConfirm, String pathToReject) {
        SimpleMailMessage messageToAdmin = new SimpleMailMessage();
        messageToAdmin.setFrom("youthofosh@gmail.com");
        messageToAdmin.setTo(adminEmail);
        messageToAdmin.setSubject("Volunteer make request to system");
        messageToAdmin.setText("Name: " + volunteer.getName() +
                "\nGender: " + volunteer.getGender() +
                "\nAge: " + volunteer.getAge() +
                "\nTown: " + volunteer.getTown() +
                "\nComment: " + volunteer.getComment() +
                "\nContacts: " + volunteer.getContacts() +
                "\n\n\nAccept: " + pathToConfirm +
                "\t\tReject: " + pathToReject);
        return messageToAdmin;
    }

    public void sendPassword(String to ){
        try{

            Optional<User> user = userRepository.findByEmail(to);
            String password = generatorService.generatePassword();
            user.get().setPassword(encoder.encode(password));
            userRepository.save(user.get());
            String email = to;
            if (email == null)
                throw new BadCredentialsException("Please, write your email");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ecomarket1111@gmail.com");
            message.setTo(to);
            message.setText("This is your new password  " + password);
            message.setSubject("Youth of Osh. Password for auth.");
            mailSender.send(message);
        }catch (MailException e ){
            throw new BadRequestException("Email is invalid , please enter correct email");
        }

    }

}
