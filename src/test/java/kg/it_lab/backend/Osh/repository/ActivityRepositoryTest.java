//package kg.it_lab.backend.Osh.repository;
//
//import kg.it_lab.backend.Osh.entities.Activity;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class ActivityRepositoryTest {
//
//    @Autowired
//    private ActivityRepository activityRepository;
//
//    private final Activity activity = new Activity();
//
//    @BeforeEach
//    void setUp() {
//        activity.setYear(2024);
//        activity.setName("example");
//        activity.setDescription("It's description for activity");
//        activity.setImage(null);
//        activityRepository.save(activity);
//    }
//
//    @AfterEach
//    void tearDown() {
//        activityRepository.deleteAll();
//    }
//
//    @Test
//    void itShouldFindActivityByName() {
//        Activity findActivityByName = activityRepository.findByName(activity.getName()).orElse(null);
//
//        assertNotNull(findActivityByName);
//        assertEquals(activity.getName(), findActivityByName.getName());
//    }
//}