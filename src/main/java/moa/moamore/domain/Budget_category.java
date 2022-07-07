package moa.moamore.domain;


import lombok.Getter;

import javax.persistence.*;


@Getter
@Entity
@Table(name = "budget_category")
@IdClass(Budget_category_id.class)
public class Budget_category extends BaseEntity {

    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    private Budget budget;

    private int amount;

    private int left_amount;

    public Budget_category(Category category, Budget budget, int amount, int left_amount) {
        this.category = category;
        this.budget = budget;
        this.amount = amount;
        this.left_amount = left_amount;
    }

    public Budget_category() {

    }

    public void minusAmount(int amount) {
        int restAmount = this.left_amount - amount;
        this.left_amount = restAmount;
    }
}
