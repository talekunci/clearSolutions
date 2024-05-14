package clear.solutions.assignment.entities;

import clear.solutions.assignment.validation.DateRangeValidation;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DateRangeValidation
public class DateRange {

    @Nullable
    private Date from;
    @Nullable
    private Date to;

}
