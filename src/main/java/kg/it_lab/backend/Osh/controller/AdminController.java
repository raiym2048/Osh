package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.auth.MyData;
import kg.it_lab.backend.Osh.dto.category.CategoryRequest;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;

import kg.it_lab.backend.Osh.service.AdminService;

import kg.it_lab.backend.Osh.service.EventService;
import kg.it_lab.backend.Osh.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final EventService eventService;
    private final NewsService newsService;

    @PostMapping("/news/add")
    public MyData add(@RequestBody NewsRequest newsRequest ){
        adminService.add(newsRequest);
        MyData data = new MyData();
        data.setMessage("News added successfully");
        return data;
    }
    @PutMapping("/news/updateByName/{name}")
    public MyData updateByName(@PathVariable String name, @RequestBody NewsRequest newsRequest) {
        adminService.updateByName(name, newsRequest);
        MyData data = new MyData();
        data.setMessage("News updated successfully");
        return data;
    }
    @DeleteMapping("/news/deleteByName/{name}")
    public MyData deleteByName(@PathVariable String name) {
        adminService.deleteByName(name);
        MyData data = new MyData();
        data.setMessage("News deleted successfully");
        return data;
    }
    @PostMapping("/event/add")
    public MyData add( @RequestBody EventRequest eventRequest){
        adminService.addEvent(eventRequest);
        MyData data = new MyData();
        data.setMessage("Event added successfully");
        return data;
    }
    @PutMapping("/event/updateByName/{name}")
    public MyData update(@PathVariable String name, @RequestBody EventRequest eventRequest) {
        adminService.updateEvent(name, eventRequest);
        MyData data = new MyData();
        data.setMessage("Event updated successfully");
        return data;
    }
    @DeleteMapping("/event/deleteByName/{name}")
    public MyData delete(@PathVariable String name){
        adminService.deleteEvent(name);
        MyData data = new MyData();
        data.setMessage("Event deleted successfully");
        return data;
    }
    @PostMapping("/category/add")
    public MyData addCategory(@RequestBody CategoryRequest categoryRequest){
        adminService.addCategory(categoryRequest);
        MyData data = new MyData();
        data.setMessage("Category added successfully");
        return data;
    }
    @DeleteMapping("/category/deleteByName/{name}")
    public MyData deleteCategory(@PathVariable String name){
        adminService.deleteCategory(name);
        MyData data = new MyData();
        data.setMessage("Category was successfully deleted");
        return data;
    }

    @PostMapping("/event/{eventName}/image/{imageName}")
    public void attachImageToEvent(@PathVariable String imageName, @PathVariable String eventName) {
        eventService.attachImageToEvent(eventName, imageName);
    }

    @PostMapping("/news/{newsName}/image/{imageName}")
    public void attachImageToNews(@PathVariable String imageName, @PathVariable String newsName) {
        newsService.attachImageToNews(newsName, imageName);
    }

}
