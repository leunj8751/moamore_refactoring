package moa.moamore;


import moa.moamore.domain.Category;
import moa.moamore.domain.Member;
import moa.moamore.domain.Money_type;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void add_category() {

        Member member = new Member("leunj8751@naver.com", "1234", "까비");

        Category category1 = new Category(member, "교육", Money_type.expense);
        Category category2 = new Category(member, "쇼핑", Money_type.expense);

        memberRepository.save(member);
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        List<Category> categoryList = categoryRepository.findByMemberAndType(Money_type.expense, member);


        assertEquals(2, categoryList.size());
        assertEquals("교육", categoryList.get(0).getCategory_name());
        assertEquals("쇼핑", categoryList.get(1).getCategory_name());
        assertEquals("까비", member.getNickname());

    }


}
