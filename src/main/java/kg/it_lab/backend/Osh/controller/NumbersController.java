package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.numbers.NumbersResponse;
import kg.it_lab.backend.Osh.service.NumbersService;
import kg.it_lab.backend.Osh.service.admin.AdminNumbersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/numbers")
@RequiredArgsConstructor
public class NumbersController {

    private NumbersService numbersService;

    @GetMapping("/all")
    public List<NumbersResponse> all(){
        return numbersService.all();
    }
}
