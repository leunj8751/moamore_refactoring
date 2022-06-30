package moa.moamore.domain;


import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "except_budget")
public class Except_budget extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "except_budget_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private int amount;

    private String content;

    private String memo;


    public Except_budget() {

    }

    public Except_budget(Member member, Category category, int amount, String content, String memo) {
        this.member = member;
        this.category = category;
        this.amount = amount;
        this.content = content;
        this.memo = memo;
    }
}
