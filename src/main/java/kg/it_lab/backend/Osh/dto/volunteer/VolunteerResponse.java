package kg.it_lab.backend.Osh.dto.volunteer;

import com.amazonaws.services.polly.model.Gender;
import lombok.Data;

@Data
public class VolunteerResponse {
    private Long id;

    private String name;
    private String gender;
    private int age;
    private String town;
    private String comment;
    private String contacts;
}
