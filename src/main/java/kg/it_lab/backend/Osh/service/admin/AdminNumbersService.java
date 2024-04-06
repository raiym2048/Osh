package kg.it_lab.backend.Osh.service.admin;

import kg.it_lab.backend.Osh.dto.numbers.NumbersRequest;

public interface AdminNumbersService {
    void addNumbers(NumbersRequest numbersRequest);

    void updateNumbers(Long id, NumbersRequest numbersRequest);

    void deleteNumbersById(Long id);
}
