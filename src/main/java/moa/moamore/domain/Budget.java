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

    private int period;

    private LocalDateTime start_day;

    private LocalDateTime end_day;

    @Enumerated(EnumType.STRING)
    private Budget_status budget_status;

    private int left_budget;

    @OneToMany(mappedBy = "budget")
    private List<Budget_expense> budget_expenseList = new ArrayList<>();



}
