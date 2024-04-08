package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.partner.PartnersResponse;
import kg.it_lab.backend.Osh.service.PartnersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/partners")
@RequiredArgsConstructor
public class PartnersController {
    private final PartnersService partnersService;

    @GetMapping("/all")
    public PartnersResponse all(){
        return partnersService.all();
    }
}
