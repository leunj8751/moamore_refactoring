package moa.moamore.dto;

import lombok.Getter;
import lombok.Setter;
import moa.moamore.domain.Money_type;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRecordDTO {

    private String memberId;
    private Long categoryId;
    private int amount;
    private String Content;
    private String memo;
    private Money_type type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;


    public ExpenseRecordDTO(String memberId, Long categoryId, int amount, String content, String memo, Money_type type, LocalDate date) {
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.Content = content;
        this.memo = memo;
        this.type = type;
        this.date = date;
    }

    public ExpenseRecordDTO() {
    }
}
