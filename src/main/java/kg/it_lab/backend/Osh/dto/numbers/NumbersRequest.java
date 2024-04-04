package kg.it_lab.backend.Osh.dto.numbers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NumbersRequest {
    private Integer businessProjectNumbers;
    private Integer socialProjectNumbers;
    private Integer projectNumbers;
    private Integer peopleNumbers;
    private Integer hubNumbers;
    private Integer newWorkPlaceNumbers;
}
