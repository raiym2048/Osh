package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.auth.EditorPasswordRequest;
import kg.it_lab.backend.Osh.dto.auth.MyData;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.news.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.dto.news.admin.AdminRegisterRequest;
import kg.it_lab.backend.Osh.service.EditorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/editor")
public class EditorController {
    private final EditorService editorService;
    @PutMapping("/news/updateByName/{name}")
    public MyData updateByName(@PathVariable String name, @RequestBody NewsRequest newsRequest) {
        editorService.updateByName(name, newsRequest);
        MyData data = new MyData();
        data.setMessage("News updated successfully");
        return data;
    }
    @PostMapping("/register")
    public MyData registerEditor(@RequestBody AdminRegisterRequest adminRegisterRequest){
        editorService.registerEditor(adminRegisterRequest);
        MyData data = new MyData();
        data.setMessage("Your new login and password was sent to "+ adminRegisterRequest.getEmail()+" !");
        return data;
    }
    @PostMapping("/login")
    public void loginEditor(@RequestBody AdminLoginRequest adminLoginRequest){
        editorService.loginEditor(adminLoginRequest);
    }
    @PutMapping("/changePassword")
    public MyData changePassword(@RequestBody EditorPasswordRequest editorPasswordRequest){
        editorService.changePassword(editorPasswordRequest);
        MyData data = new MyData();
        data.setMessage("Your password changed successfully");
        return data;
    }

}
