package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.auth.MyData;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;

import kg.it_lab.backend.Osh.dto.news.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.dto.news.admin.AdminRegisterRequest;
import kg.it_lab.backend.Osh.service.AdminService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/news/add")
    public void add(@RequestBody NewsRequest newsRequest ){
        adminService.add(newsRequest);
    }
    @PutMapping("/news/updateByName/{name}")
    public void updateByName(@PathVariable String name, @RequestBody NewsRequest newsRequest) {
        adminService.updateByName(name, newsRequest);
    }
    @DeleteMapping("/news/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        adminService.deleteByName(name);
    }
    @PostMapping("/event/add")
    public void add(@RequestBody EventRequest eventRequest){
        adminService.addEvent(eventRequest);
    }
    @PutMapping("/event/updateByName/{name}")
    public void update(@PathVariable String name, @RequestBody EventRequest eventRequest) {
        adminService.updateEvent(name, eventRequest);
    }
    @DeleteMapping("/event/deleteByName/{name}")
    public void delete(@PathVariable String name){
        adminService.deleteEvent(name);
    }


}
