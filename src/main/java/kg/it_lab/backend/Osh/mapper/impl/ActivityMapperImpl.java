package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.activity.ActivityResponse;
import kg.it_lab.backend.Osh.entities.Activity;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.mapper.ActivityMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ActivityMapperImpl implements ActivityMapper {
    @Override
    public ActivityResponse toDto(Activity activity) {
        ActivityResponse activityResponse = new ActivityResponse();
        if(activity.getImage() != null)
            activityResponse.setImagePath(activity.getImage().getPath());
        activityResponse.setName(activity.getName());
        activityResponse.setDescription(activity.getDescription());
        activityResponse.setYear(activity.getYear());

        return activityResponse;

    }

    @Override
    public List<ActivityResponse> toDtos(List<Activity> activities) {
        List<ActivityResponse> activityResponses = new ArrayList<>();
        for(Activity activity:activities){
            activityResponses.add(toDto(activity));
        }
        return activityResponses;
    }

    @Override
    public Activity toDtoActivity(Activity activity, ActivityRequest activityRequest, Image image) {
        activity.setName(activityRequest.getName());
        activity.setYear(activityRequest.getYear());
        activity.setDescription(activityRequest.getDescription());
        activity.setImage(image);
        return activity;
    }
}
