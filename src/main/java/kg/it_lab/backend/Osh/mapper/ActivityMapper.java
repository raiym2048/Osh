package kg.it_lab.backend.Osh.mapper;

import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.activity.ActivityResponse;
import kg.it_lab.backend.Osh.dto.news.NewsDetailResponse;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.news.NewsResponse;
import kg.it_lab.backend.Osh.entities.Activity;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.News;

import java.util.List;

public interface ActivityMapper {
    ActivityResponse toDto(Activity activity);
    List<ActivityResponse> toDtoS(List<Activity> activities);
    Activity toDtoActivity(Activity activity , ActivityRequest activityRequest, Image image);

}
