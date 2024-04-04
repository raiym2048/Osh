package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.numbers.NumbersRequest;
import kg.it_lab.backend.Osh.dto.numbers.NumbersResponse;
import kg.it_lab.backend.Osh.entities.Numbers;
import kg.it_lab.backend.Osh.mapper.NumbersMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class NumbersMapperImpl implements NumbersMapper {

    @Override
    public NumbersResponse toDto(Numbers numbers) {
        NumbersResponse numbersResponse = new NumbersResponse();
        numbersResponse.setProjectNumbers(numbers.getProjectNumbers());
        numbersResponse.setHubNumbers(numbers.getHubNumbers());
        numbersResponse.setPeopleNumbers(numbers.getPeopleNumbers());
        numbersResponse.setBusinessProjectNumbers(numbers.getBusinessProjectNumbers());
        numbersResponse.setSocialProjectNumbers(numbers.getSocialProjectNumbers());
        numbersResponse.setNewWorkPlaceNumbers(numbers.getNewWorkPlaceNumbers());
        return numbersResponse;

    }

    @Override
    public List<NumbersResponse> toDtoS(List<Numbers> numbers) {
        List<NumbersResponse> numbersResponses = new ArrayList<>();
        for(Numbers numbers1 : numbers){
            numbersResponses.add(toDto(numbers1));
        }
        return numbersResponses;
    }

    @Override
    public Numbers toDtoNumbers(Numbers numbers, NumbersRequest numbersRequest) {
        numbers.setHubNumbers(numbersRequest.getHubNumbers());
        numbers.setSocialProjectNumbers(numbersRequest.getSocialProjectNumbers());
        numbers.setBusinessProjectNumbers(numbersRequest.getBusinessProjectNumbers());
        numbers.setProjectNumbers(numbersRequest.getProjectNumbers());
        numbers.setPeopleNumbers(numbersRequest.getPeopleNumbers());
        numbers.setNewWorkPlaceNumbers(numbersRequest.getNewWorkPlaceNumbers());
        return numbers;


    }
}
