package kg.it_lab.backend.Osh.mapper;

import kg.it_lab.backend.Osh.dto.numbers.NumbersRequest;
import kg.it_lab.backend.Osh.dto.numbers.NumbersResponse;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectResponse;
import kg.it_lab.backend.Osh.entities.Numbers;
import kg.it_lab.backend.Osh.entities.Project;

import java.util.List;

public interface NumbersMapper {
    NumbersResponse toDto(Numbers numbers);
    List<NumbersResponse> toDtoS(List<Numbers> numbers);
    Numbers toDtoNumbers(Numbers numbers , NumbersRequest numbersRequest);
}
