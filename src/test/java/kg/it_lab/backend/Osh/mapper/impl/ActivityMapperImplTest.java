package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.activity.ActivityResponse;
import kg.it_lab.backend.Osh.entities.Activity;
import kg.it_lab.backend.Osh.entities.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ActivityMapperImplTest {

    @InjectMocks
    private ActivityMapperImpl activityMapper;

    private final Activity activity = new Activity();
    private final Image image = new Image();

    @BeforeEach
    void setUp() {
        image.setId(1L);
        image.setName("image");
        image.setPath("path");

        activity.setId(1L);
        activity.setYear(2024);
        activity.setName("name");
        activity.setDescription("description");
        activity.setImage(image);
    }

    @Test
    void toDto() {
        ActivityResponse responseResult = activityMapper.toDto(activity);

        assertEquals(activity.getId(), responseResult.getId());
        assertEquals(activity.getYear(), responseResult.getYear());
        assertEquals(activity.getName(), responseResult.getName());
        assertEquals(activity.getDescription(), responseResult.getDescription());
        assertEquals(activity.getImage().getPath(), responseResult.getImagePath());
    }

    @Test
    void toDtoS() {
        Image image1 = new Image();
        image1.setId(2L);
        image1.setName("image1");
        image1.setPath("path1");

        Activity activity1 = new Activity();
        activity1.setId(2L);
        activity1.setYear(2024);
        activity1.setName("name1");
        activity1.setDescription("description1");
        activity1.setImage(image1);

        List<Activity> activityList = new ArrayList<>();
        activityList.add(activity);
        activityList.add(activity1);

        List<ActivityResponse> responseListResult = activityMapper.toDtoS(activityList);

        assertEquals(activityList.size(), responseListResult.size());
        assertEquals(activityList.get(0).getId(), responseListResult.get(0).getId());
        assertEquals(activityList.get(1).getId(), responseListResult.get(1).getId());
        assertEquals(activityList.get(0).getImage().getPath(), responseListResult.get(0).getImagePath());
        assertEquals(activityList.get(1).getImage().getPath(), responseListResult.get(1).getImagePath());
    }

    @Test
    void toDtoActivity() {
        ActivityRequest activityRequest = new ActivityRequest();
        activityRequest.setYear(2024);
        activityRequest.setName("name");
        activityRequest.setDescription("description");

        Activity activityResult = activityMapper.toDtoActivity(new Activity(), activityRequest, image);
        assertEquals(activity.getYear(), activityResult.getYear());
        assertEquals(activity.getName(), activityResult.getName());
        assertEquals(activity.getDescription(), activityResult.getDescription());
        assertEquals(activity.getImage(), activityResult.getImage());
    }
}