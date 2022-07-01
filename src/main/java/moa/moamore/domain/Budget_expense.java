package moa.moamore.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "budget_expense")
public class Budget_expense extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "budget_expense_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private int amount;

    private String content;

    private String memo;

    private int sum_amount;

    public Budget_expense(Budget budget, Category category, int amount, String content, String memo) {
        this.budget = budget;
        this.category = category;
        this.amount = amount;
        this.content = content;
        this.memo = memo;
    }

    public Budget_expense() {

    }


}
