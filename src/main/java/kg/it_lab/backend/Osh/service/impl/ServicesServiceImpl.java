package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.service.ServicesResponse;
import kg.it_lab.backend.Osh.mapper.ServicesMapper;
import kg.it_lab.backend.Osh.repository.ServicesRepository;
import kg.it_lab.backend.Osh.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicesServiceImpl implements ServicesService {

    private final ServicesRepository servicesRepository;
    private final ServicesMapper servicesMapper;

    @Override
    public List<ServicesResponse> all() {
        return servicesMapper.toDtoS(servicesRepository.findAll());
    }
}
