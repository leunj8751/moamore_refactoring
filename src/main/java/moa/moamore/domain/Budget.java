package moa.moamore.domain;


import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "budget")
public class Budget extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "budget_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int total_budget;

    @Convert(converter = Budget_periodConverter.class)
    private Budget_period period;

    private LocalDate start_day;

    private LocalDate end_day;

    @Enumerated(EnumType.STRING)
    private Budget_status budget_status;

    private int left_budget;

    @OneToMany(mappedBy = "budget")
    private List<Budget_expense> budget_expenseList = new ArrayList<>();

    @OneToMany(mappedBy = "budget")
    private List<Budget_category> budget_categoryList = new ArrayList<>();


    /* 생성 메소드 */

    public Budget() {
    }


    public Budget(Member member, int total_budget, int left_budget, Budget_period period, Budget_status budget_status, LocalDate start_day, LocalDate end_day) {
        this.member = member;
        this.total_budget = total_budget;
        this.left_budget = left_budget;
        this.period = period;
        this.budget_status = budget_status;
        this.start_day = start_day;
        this.end_day = end_day;
    }

    public static Budget createBudget(Member member, int total_budget, Budget_period budget_period) {

        LocalDate start_day = LocalDate.now();
        LocalDate end_day = LocalDate.now().plusDays(budget_period.getValue());

        Budget budget = new Budget(member, total_budget, total_budget, budget_period, Budget_status.ongoing, start_day, end_day);

        return budget;
    }

    public void minusAmount(int amount) {
        int restAmount = this.total_budget - amount;
        this.left_budget = restAmount;
    }


    public void endBudget() {
        this.budget_status = Budget_status.end;
        this.end_day = LocalDate.now().minusDays(1);
    }



}
