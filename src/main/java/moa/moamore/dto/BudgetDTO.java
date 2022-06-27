package moa.moamore.dto;


import lombok.Getter;
import lombok.Setter;
import moa.moamore.domain.Budget_expense;
import moa.moamore.domain.Budget_status;
import moa.moamore.domain.Category;
import moa.moamore.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BudgetDTO {

    private String memberId;
    private int total_budget;
    private int period;

    private List<CategoryDTO> categoryList = new ArrayList<>();
}
