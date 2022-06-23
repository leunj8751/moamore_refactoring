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

    private int amount;

    private int left_amount;

}
