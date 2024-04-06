package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.hub.HubResponse;
import kg.it_lab.backend.Osh.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hub")
public class HubController {

    private final HubService hubService;

    public List<HubResponse> all(){
        return hubService.all();
    }

}
