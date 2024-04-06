package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.numbers.NumbersResponse;
import kg.it_lab.backend.Osh.mapper.NumbersMapper;
import kg.it_lab.backend.Osh.repository.NumbersRepository;
import kg.it_lab.backend.Osh.service.NumbersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NumbersServiceImpl implements NumbersService {

    private final NumbersMapper numbersMapper;
    private final NumbersRepository numbersRepository;

    @Override
    public List<NumbersResponse> all() {
        return numbersMapper.toDtoS(numbersRepository.findAll());
    }
}
