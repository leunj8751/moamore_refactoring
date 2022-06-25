package moa.moamore.domain;


import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="member")
public class Member extends BaseEntity{

    @Id
    @Column(name="member_id")
    private String id;

    private String password;

    private String nickname;

    @OneToMany(mappedBy = "member")
    private List<Budget> budgetList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Category> categoryList = new ArrayList<>();

    public Member(String id, String password, String nickname) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
    }

    public Member() {

    }
}
