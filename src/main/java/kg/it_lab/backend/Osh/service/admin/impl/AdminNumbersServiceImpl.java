package kg.it_lab.backend.Osh.service.admin.impl;

import kg.it_lab.backend.Osh.dto.numbers.NumbersRequest;
import kg.it_lab.backend.Osh.entities.Numbers;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.*;
import kg.it_lab.backend.Osh.repository.*;
import kg.it_lab.backend.Osh.service.ImageService;
import kg.it_lab.backend.Osh.service.admin.AdminNumbersService;
import kg.it_lab.backend.Osh.service.admin.AdminService;
import kg.it_lab.backend.Osh.service.emailSender.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminNumbersServiceImpl implements AdminNumbersService {

    private final NumbersRepository numbersRepository;
    private final NumbersMapper numbersMapper;
    private final MessageSource messageSource;

    @Override
    public void addNumbers(NumbersRequest numbersRequest) {
        Numbers numbers = new Numbers();
        numbersRepository.save(numbersMapper.toDtoNumbers(numbers , numbersRequest ));
    }

    @Override
    public void updateNumbers(Long id , NumbersRequest numbersRequest) {
        Optional<Numbers> numbers = numbersRepository.findById(id);
        if(numbersRepository.findById(id).isEmpty()){
            throw new NotFoundException(messageSource.getMessage("numbers.notfound", null, LocaleContextHolder.getLocale()) , HttpStatus.NOT_FOUND);
        }
        numbersRepository.save(numbersMapper.toDtoNumbers(numbers.get() , numbersRequest ));
    }

    @Override
    public void deleteNumbersById(Long id) {
        Optional<Numbers> numbers = numbersRepository.findById(id);
        if(numbersRepository.findById(id).isEmpty()){
            throw new NotFoundException(messageSource.getMessage("numbers.notfound", null, LocaleContextHolder.getLocale()) , HttpStatus.NOT_FOUND);
        }
        numbersRepository.deleteById(id);


    }
}
