package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.numbers.NumbersRequest;
import kg.it_lab.backend.Osh.dto.numbers.NumbersResponse;
import kg.it_lab.backend.Osh.entities.Numbers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NumbersMapperImplTest {

    @InjectMocks
    private NumbersMapperImpl numbersMapper;

    private final Numbers numbers = new Numbers();
    @BeforeEach
    void setUp() {
        numbers.setId(1L);
        numbers.setBusinessProjectNumbers(10);
        numbers.setSocialProjectNumbers(5);
        numbers.setProjectNumbers(15);
        numbers.setPeopleNumbers(100);
        numbers.setHubNumbers(30);
        numbers.setNewWorkPlaceNumbers(20);
    }

    @Test
    void toDto() {
        NumbersResponse responseResult = numbersMapper.toDto(numbers);

        assertEquals(numbers.getId(), responseResult.getId());
        assertEquals(numbers.getBusinessProjectNumbers(), responseResult.getBusinessProjectNumbers());
        assertEquals(numbers.getSocialProjectNumbers(), responseResult.getSocialProjectNumbers());
        assertEquals(numbers.getProjectNumbers(), responseResult.getProjectNumbers());
        assertEquals(numbers.getPeopleNumbers(), responseResult.getPeopleNumbers());
        assertEquals(numbers.getHubNumbers(), responseResult.getHubNumbers());
        assertEquals(numbers.getNewWorkPlaceNumbers(), responseResult.getNewWorkPlaceNumbers());
    }

    @Test
    void toDtoS() {
        Numbers numbers1 = new Numbers();

        numbers1.setId(2L);
        numbers1.setBusinessProjectNumbers(100);
        numbers1.setSocialProjectNumbers(50);
        numbers1.setProjectNumbers(150);
        numbers1.setPeopleNumbers(1000);
        numbers1.setHubNumbers(300);
        numbers1.setNewWorkPlaceNumbers(200);

        List<Numbers> numbersList = new ArrayList<>();
        numbersList.add(numbers);
        numbersList.add(numbers1);

        List<NumbersResponse> responseListResult = numbersMapper.toDtoS(numbersList);

        assertEquals(numbersList.size(), responseListResult.size());
        assertEquals(numbersList.get(0).getId(), responseListResult.get(0).getId());
        assertEquals(numbersList.get(1).getNewWorkPlaceNumbers(), responseListResult.get(1).getNewWorkPlaceNumbers());
    }

    @Test
    void toDtoNumbers() {
        NumbersRequest numbersRequest = new NumbersRequest();
        numbersRequest.setBusinessProjectNumbers(10);
        numbersRequest.setSocialProjectNumbers(5);
        numbersRequest.setProjectNumbers(15);
        numbersRequest.setPeopleNumbers(100);
        numbersRequest.setHubNumbers(30);
        numbersRequest.setNewWorkPlaceNumbers(20);

        Numbers numbersResult = numbersMapper.toDtoNumbers(new Numbers(), numbersRequest);

        assertEquals(numbers.getNewWorkPlaceNumbers(), numbersResult.getNewWorkPlaceNumbers());
        assertEquals(numbers.getHubNumbers(), numbersResult.getHubNumbers());
        assertEquals(numbers.getPeopleNumbers(), numbersRequest.getPeopleNumbers());
        assertEquals(numbers.getProjectNumbers(), numbersResult.getProjectNumbers());
        assertEquals(numbers.getSocialProjectNumbers(), numbersResult.getSocialProjectNumbers());
        assertEquals(numbers.getBusinessProjectNumbers(), numbersResult.getBusinessProjectNumbers());
    }
}