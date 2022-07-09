package moa.moamore.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Budget_expenseDTO implements Comparable<Budget_expenseDTO>{

    private CategoryDTO category;
    private int amount;
    private String content;
    private int sum_amount;
    private String type;


    @Override
    public int compareTo(Budget_expenseDTO o) {
        return o.sum_amount - this.sum_amount;

    }


}
