package moa.moamore.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BudgetDTO {

    private long id;
    private String memberId;
    private int total_budget;
    private int period;
    private LocalDate start_day;
    private LocalDate end_day;

    private List<CategoryDTO> categoryList = new ArrayList<>();

    public BudgetDTO(String memberId, List<CategoryDTO> categoryList) {
        this.memberId = memberId;
        this.categoryList = categoryList;
    }

    public BudgetDTO(String memberId, int total_budget, int period, List<CategoryDTO> categoryList) {
        this.memberId = memberId;
        this.total_budget = total_budget;
        this.period = period;
        this.categoryList = categoryList;
    }

    public BudgetDTO() {
    }
}
