package moa.moamore;

import moa.moamore.domain.Category;
import moa.moamore.domain.Member;
import moa.moamore.domain.Money_type;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.repository.MemberRepository;
import moa.moamore.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;



    @Test
    public void add_member(){

        Member member = new Member("leunj8751","1234","까비");
        memberService.join(member);

        Member findMember = memberRepository.findOne(member.getId());
        assertEquals(member.getId(),findMember.getId());


        List<Category> categoryList = categoryRepository.findByMember(member);
        List<Category> expenseCategoryList = categoryRepository.findByType(Money_type.expense);

        assertEquals(6,expenseCategoryList.size());

    }

    @Test()
    public void duplicate_add_member(){

    }

}
