package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.service.ServicesResponse;
import kg.it_lab.backend.Osh.entities.Services;
import kg.it_lab.backend.Osh.mapper.ServicesMapper;
import kg.it_lab.backend.Osh.repository.ServicesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicesServiceImplTest {

    @InjectMocks
    private ServicesServiceImpl servicesService;

    @Mock
    private ServicesRepository servicesRepository;
    @Mock
    private ServicesMapper servicesMapper;

    private final Services services = new Services();
    private final ServicesResponse servicesResponse = new ServicesResponse();

    @Test
    void all() {
        when(servicesRepository.findAll()).thenReturn(List.of(services));
        when(servicesMapper.toDtoS(anyList())).thenReturn(List.of(servicesResponse));

        List<ServicesResponse> responseList = servicesService.all();

        assertNotNull(responseList);
        assertFalse(responseList.isEmpty());
        assertEquals(servicesResponse, responseList.get(0));

        verify(servicesRepository).findAll();
        verify(servicesMapper).toDtoS(anyList());
    }
}