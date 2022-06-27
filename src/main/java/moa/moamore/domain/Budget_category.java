package moa.moamore.domain;


import lombok.Getter;

import javax.persistence.*;


@Getter
@Entity
@Table(name="budget_category")
@IdClass(Budget_category_id.class)
public class Budget_category {

    @Id
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @Id
    @ManyToOne
    @JoinColumn(name="budget_id")
    private Budget budget;

    @Id
    @ManyToOne
    @JoinColumn(name="memeber_id")
    private Member member;
    private int amount;

    private int left_amount;

    public Budget_category(Category category, Budget budget, Member member,int amount, int left_amount) {
        this.category = category;
        this.budget = budget;
        this.member = member;
        this.amount = amount;
        this.left_amount = left_amount;
    }

    public Budget_category() {

    }
}
