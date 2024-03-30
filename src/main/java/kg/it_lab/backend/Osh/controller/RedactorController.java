package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.service.RedactorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/redactor")
public class RedactorController {
    private final RedactorService redactorService;
    @PutMapping("/news/updateByName/{name}")
    public void updateByName(@PathVariable String name, @RequestBody NewsRequest newsRequest) {
        redactorService.updateByName(name, newsRequest);
    }
}
