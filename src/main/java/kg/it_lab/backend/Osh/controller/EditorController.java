package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.auth.EditorPasswordRequest;
import kg.it_lab.backend.Osh.dto.auth.MyData;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.service.EditorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/editor")
public class EditorController {
    private final EditorService editorService;
    @PutMapping("/news/updateByName/{newsName}/{imageName}")
    public MyData updateByName(@PathVariable String newsName, @RequestBody NewsRequest newsRequest , @PathVariable String imageName) {
        editorService.updateByName(newsName, newsRequest , imageName);
        MyData data = new MyData();
        data.setMessage("News updated successfully");
        return data;
    }


    @PostMapping("/login")
    public void loginEditor(@RequestBody AdminLoginRequest adminLoginRequest){
        editorService.loginEditor(adminLoginRequest);
    }
    @PutMapping("/changePassword")
    public MyData changePassword(@RequestHeader("Authorization") String token ,  @RequestBody EditorPasswordRequest editorPasswordRequest){
        editorService.changePassword( token , editorPasswordRequest);
        MyData data = new MyData();
        data.setMessage("Your password changed successfully");
        return data;
    }

}
