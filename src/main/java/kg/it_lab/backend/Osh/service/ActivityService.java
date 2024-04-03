package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.activity.ActivityResponse;

import java.util.List;

public interface ActivityService {
    List<ActivityResponse> all();
}
