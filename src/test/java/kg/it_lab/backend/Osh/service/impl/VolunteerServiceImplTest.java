package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.volunteer.VolunteerRequest;
import kg.it_lab.backend.Osh.dto.volunteer.VolunteerResponse;
import kg.it_lab.backend.Osh.entities.Volunteer;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.VolunteerMapper;
import kg.it_lab.backend.Osh.mapper.impl.VolunteerMapperImpl;
import kg.it_lab.backend.Osh.repository.VolunteerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceImplTest {

    @Mock
    private VolunteerRepository volunteerRepository;
    @Mock
    private VolunteerMapperImpl volunteerMapper;

    @InjectMocks
    private VolunteerServiceImpl volunteerService;

    private final Volunteer volunteer = new Volunteer();
    private final VolunteerResponse volunteerResponse = new VolunteerResponse();
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() {
        when(volunteerRepository.findAll()).thenReturn(List.of(volunteer));
        when(volunteerMapper.toDtoS(anyList())).thenReturn(List.of(volunteerResponse));

        List<VolunteerResponse> responseList = volunteerService.getAll();

        assertNotNull(responseList);
        assertFalse(responseList.isEmpty());
        assertEquals(volunteerResponse, responseList.get(0));

        verify(volunteerRepository).findAll();
        verify(volunteerMapper).toDtoS(anyList());
    }

    @Test
    void finByIdReturnVolunteerResponse() {
        when(volunteerRepository.findById(anyLong())).thenReturn(Optional.of(volunteer));
        when(volunteerMapper.toDto(volunteer)).thenReturn(volunteerResponse);

        VolunteerResponse responseResult = volunteerService.finById(1L);

        assertNotNull(responseResult);
        assertEquals(volunteerResponse, responseResult);

        verify(volunteerRepository).findById(anyLong());
        verify(volunteerMapper).toDto(any(Volunteer.class));
    }

    @Test
    void findByIdThrowsNotFoundException() {
        when(volunteerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> volunteerService.finById(1L));

        verify(volunteerRepository).findById(anyLong());
        verify(volunteerMapper, never()).toDto(any(Volunteer.class));
    }

    @Test
    void addVolunteer() {
        VolunteerRequest volunteerRequest = new VolunteerRequest();
        ArgumentCaptor<Volunteer> argumentCaptor = ArgumentCaptor.forClass(Volunteer.class);

        when(volunteerMapper.toDtoVolunteer(any(Volunteer.class), eq(volunteerRequest))).thenReturn(volunteer);

        volunteerService.addVolunteer(volunteerRequest);

        verify(volunteerMapper).toDtoVolunteer(any(Volunteer.class), eq(volunteerRequest));
        verify(volunteerRepository).save(argumentCaptor.capture());

        Volunteer capturedVolunteer = argumentCaptor.getValue();
        assertThat(capturedVolunteer).isEqualTo(volunteer);
    }
}