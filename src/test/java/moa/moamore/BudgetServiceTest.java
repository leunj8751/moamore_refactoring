package moa.moamore;

import moa.moamore.domain.*;
import moa.moamore.dto.BudgetDTO;
import moa.moamore.dto.CategoryDTO;
import moa.moamore.repository.BudgetRepository;
import moa.moamore.repository.MemberRepository;
import moa.moamore.service.BudgetService;
import moa.moamore.service.CategoryService;
import moa.moamore.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class BudgetServiceTest {


    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private BudgetRepository budgetRepository;

    @Test
    public void 새로운예산설정() {

        String member_id = "leunj8752";

        Member member = new Member(member_id, "1234", "까비");
        memberService.join(member);


        int total_budget = 200000;
        Budget_period period = Budget_period.valueOf(7);

        List<Category> categoryList = categoryService.getExpenseCategories(member_id);
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for (Category c : categoryList) {
            if (c.getCategory_name().equals("건강")) {
                categoryDTOList.add(new CategoryDTO(c.getId(), c.getCategory_name(), 100000));
            } else if (c.getCategory_name().equals("교육")) {
                categoryDTOList.add(new CategoryDTO(c.getId(), c.getCategory_name(), 100000));
            }
            categoryDTOList.add(new CategoryDTO(c.getId(), c.getCategory_name()));
        }
        BudgetDTO budgetDTO = new BudgetDTO();

        budgetDTO.setMemberId(member_id);
        budgetDTO.setTotal_budget(total_budget);
        budgetDTO.setPeriod(period.intValue());
        budgetDTO.setCategoryList(categoryDTOList);

        budgetService.setNewBudget(budgetDTO);


        Budget budget = budgetRepository.findOne(member);

        List<Budget_category> budget_categoryList = budgetRepository.findBudget_category(member, budget);

        //상태가 맞게 들어갔는지
        assertEquals(Budget_status.ongoing, budget.getBudget_status());
        //기간이 맞게 들어갔는지
        assertEquals(7, budget.getPeriod().intValue());


        assertEquals(2, budget_categoryList.size());

        // 새로운 예산이 시작되면 이전 예산의 상태는 end로 바껴야함


    }


    @Test
    public void categoryFindTopExpense(){



        List<Budget_expense> categoryList = budgetService.findBudgetExpenseList(LocalDate.now(),"");

        System.out.println("size :"+categoryList.size());

        for(Budget_expense be : categoryList){
            System.out.println("dddate :"+be.getCreated_date());
            System.out.println("content"+be.getContent());
        }

    }



}
