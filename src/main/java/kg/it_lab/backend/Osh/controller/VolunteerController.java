package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.volunteer.VolunteerRequest;
import kg.it_lab.backend.Osh.dto.volunteer.VolunteerResponse;
import kg.it_lab.backend.Osh.service.VolunteerService;
import kg.it_lab.backend.Osh.service.emailSender.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/volunteer")
public class VolunteerController {
    private final VolunteerService service;

    @GetMapping("/getAll")
    public List<VolunteerResponse> getAll() {
        return service.getAll();
    }

    @ExceptionHandler(Exception.class)
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> finById(@PathVariable Long id) {
        return new ResponseEntity<>(service.finById(id), HttpStatus.OK);
    }

    @PostMapping("/addVolunteer")
    public ResponseEntity<?> addVolunteer(@RequestBody VolunteerRequest volunteerRequest) {
        service.addVolunteer(volunteerRequest);
        return new ResponseEntity<>("Your request send to admin!", HttpStatus.OK);
    }
}
