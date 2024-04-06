package kg.it_lab.backend.Osh.service.admin;

import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;

public interface AdminActivityService {
    void addActivity(ActivityRequest activityRequest, Long imageId);

    void updateActivity(Long id, ActivityRequest activityRequest, Long imageId);

    void deleteActivity(Long id);
}
