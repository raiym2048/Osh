package kg.it_lab.backend.Osh.dto.volunteer;

import com.amazonaws.services.polly.model.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VolunteerRequest {
    private String name;
    private String gender;
    private LocalDate dateOfBirth;
    private String town;
    private String comment;
    private String contacts;
}
