package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.service.EditorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/editor")
public class EditorController {
    private final EditorService editorService;
    @PutMapping("/news/updateByName/{name}")
    public void updateByName(@PathVariable String name, @RequestBody NewsRequest newsRequest) {
        editorService.updateByName(name, newsRequest);
    }
}
