package moa.moamore.domain;


import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "except_budget")
public class Except_budget extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "except_budget_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private int amount;

    private String content;

    private String memo;

    private LocalDate expense_date;

    @Enumerated(EnumType.STRING)
    private Money_type type;


    public Except_budget() {

    }

    public Except_budget(Member member, Category category, int amount, String content, String memo, Money_type type) {
        this.member = member;
        this.category = category;
        this.amount = amount;
        this.content = content;
        this.memo = memo;
        this.type = type;
        this.expense_date = LocalDate.now();
    }
}
