package moa.moamore.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "category")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String category_name;

    @Enumerated(EnumType.STRING)
    private Money_type category_type;


    public Category(Member member, String category_name, Money_type category_type) {
        this.member = member;
        this.category_name = category_name;
        this.category_type = category_type;
    }

    public Category() {

    }



}
