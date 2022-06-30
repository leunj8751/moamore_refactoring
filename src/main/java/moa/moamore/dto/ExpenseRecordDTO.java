package moa.moamore.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRecordDTO {

    private String memberId;
    private Long categoryId;
    private int amount;
    private String getContent;
    private String memo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;


}
