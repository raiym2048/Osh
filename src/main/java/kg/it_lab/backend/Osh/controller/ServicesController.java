package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.service.ServicesResponse;
import kg.it_lab.backend.Osh.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServicesController {
    private final ServicesService servicesService;

    @GetMapping("/all")
    public List<ServicesResponse> all(){
        return servicesService.all();
    }
}
