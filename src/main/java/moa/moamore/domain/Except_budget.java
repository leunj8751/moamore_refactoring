package moa.moamore.domain;


import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="except_budget")
public class Except_budget extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name="except_budget_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name="category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    private Money_type type;

    private int amount;

    private String content;

    private String memo;


}
