package moa.moamore.dto;

import lombok.Getter;
import lombok.Setter;
import moa.moamore.domain.Category;


@Getter
@Setter
public class Budget_expenseDTO {

    private Category category;
    private int amount;
    private String content;
    private int sum_amount;
    private String type;

}
