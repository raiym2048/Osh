package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.activity.ActivityResponse;
import kg.it_lab.backend.Osh.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/all")
    public List<ActivityResponse> all(){
        return activityService.all();
    }
}
