package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.numbers.NumbersResponse;

import java.util.List;

public interface NumbersService {
    List<NumbersResponse> all();
}
