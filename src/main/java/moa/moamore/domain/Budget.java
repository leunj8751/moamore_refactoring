package moa.moamore.domain;


import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="budget")
public class Budget extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name="budget_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    private int total_budget;

    @Enumerated(EnumType.STRING)
    private Budget_period period;

    private LocalDateTime start_day;

    private LocalDateTime end_day;

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


    public Budget(Member member, int total_budget,int left_budget, Budget_period period ,Budget_status budget_status, LocalDateTime end_day) {
        this.member = member;
        this.total_budget = total_budget;
        this.left_budget = left_budget;
        this.period = period;
        this.budget_status = budget_status;
        this.end_day = end_day;
    }

    public static Budget createBudget(Member member, int total_budget, Budget_period budget_period){

        LocalDateTime end_day = LocalDateTime.now().plusDays(budget_period.intValue());

        Budget budget = new Budget(member,total_budget,total_budget,budget_period,Budget_status.ongoing,end_day);

        return budget;
    }

    public void addBudgetCategory(Budget_category budget_category){
        budget_categoryList.add(budget_category);
    }


}
