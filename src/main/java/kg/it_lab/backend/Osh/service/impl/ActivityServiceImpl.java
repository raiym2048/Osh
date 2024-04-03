package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.activity.ActivityResponse;
import kg.it_lab.backend.Osh.mapper.ActivityMapper;
import kg.it_lab.backend.Osh.repository.ActivityRepository;
import kg.it_lab.backend.Osh.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    @Override
    public List<ActivityResponse> all() {
        return activityMapper.toDtoS(activityRepository.findAll());
    }
}
