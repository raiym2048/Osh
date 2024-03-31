package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.auth.MyData;
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
    @PostMapping("/register")
    public MyData registerAdmin(@RequestBody AdminRegisterRequest adminRegisterRequest){
        adminService.registerAdmin(adminRegisterRequest);
        MyData data = new MyData();
        data.setMessage("Your new login and password was sent to "+ adminRegisterRequest.getEmail()+" !");
        return data;
    }
    @PostMapping("/login")
    public void loginAdmin(@RequestBody AdminLoginRequest adminLoginRequest){
        adminService.loginAdmin(adminLoginRequest);
    }

}
