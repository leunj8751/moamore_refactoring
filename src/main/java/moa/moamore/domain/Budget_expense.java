package moa.moamore.domain;


import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="budget_expense")
public class Budget_expense extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name="budget_expense_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="budget_id")
    private Budget budget;

    @OneToOne
    @JoinColumn(name="category_id")
    private Category category;

    private int amount;

    private String content;

    private String memo;



}
