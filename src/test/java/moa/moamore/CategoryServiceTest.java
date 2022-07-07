package moa.moamore;


import moa.moamore.domain.Category;
import moa.moamore.domain.Member;
import moa.moamore.domain.Money_type;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.repository.MemberRepository;
import moa.moamore.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void 카테고리추가() {

        Member member = new Member("lim01k", "1234", "까비");

        Category category1 = new Category(member, "교육", Money_type.expense);
        Category category2 = new Category(member, "쇼핑", Money_type.expense);

        memberRepository.save(member);
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        List<Category> categoryList = categoryRepository.findByMemberAndType(Money_type.expense, "lim01k");


        assertEquals(2, categoryList.size());
        assertEquals("교육", categoryList.get(0).getCategory_name());
        assertEquals("쇼핑", categoryList.get(1).getCategory_name());
        assertEquals("까비", member.getNickname());

    }






}
