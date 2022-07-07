package moa.moamore.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "budget_expense")
public class Budget_expense extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "budget_expense_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private int amount;

    private String content;

    private String memo;

    @Transient
    private int sum_amount;

    private LocalDate expense_date;

    public Budget_expense(Budget budget, Category category, int amount, String content, String memo) {
        this.budget = budget;
        this.category = category;
        this.amount = amount;
        this.content = content;
        this.memo = memo;
        this.expense_date = LocalDate.now();
    }

    public Budget_expense() {

    }


}
