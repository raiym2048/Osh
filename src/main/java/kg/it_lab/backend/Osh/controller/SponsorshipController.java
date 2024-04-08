package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipResponse;
import kg.it_lab.backend.Osh.service.SponsorshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sponsorship")
@RequiredArgsConstructor
public class SponsorshipController {

    private final SponsorshipService sponsorshipService;

    @GetMapping("/all")
    public List<SponsorshipResponse> all(){
        return sponsorshipService.all();
    }

}
