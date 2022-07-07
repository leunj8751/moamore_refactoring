package moa.moamore;

import moa.moamore.domain.*;
import moa.moamore.dto.BudgetDTO;
import moa.moamore.dto.CategoryDTO;
import moa.moamore.dto.ExpenseRecordDTO;
import moa.moamore.repository.BudgetRepository;
import moa.moamore.repository.MemberRepository;
import moa.moamore.service.BudgetService;
import moa.moamore.service.CategoryService;
import moa.moamore.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
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

    @Autowired
    private EntityManager em;

    List<CategoryDTO> categoryList;
    String memberId = "lim01k";
    Member member = new Member(memberId,"1234","이은지");
    @BeforeEach
    public void setUp(){

        memberRepository.save(member);
        System.out.println("_______________________");
        categoryList = Arrays.asList(
                new CategoryDTO(1L,"식비",300000),
                new CategoryDTO(2L,"건강",50000),
                new CategoryDTO(3L,"교육",100000),
                new CategoryDTO(4L,"패션",150000)
        );
    }


    @Test
    public void 일주일예산설정() {

        BudgetDTO budgetDTO = new BudgetDTO(memberId,500000,Budget_period.weeek.getValue(), categoryList);
        budgetService.setNewBudget(budgetDTO);
        System.out.println("#####################################33");
//        Budget budget = budgetRepository.findOne(member);
//        List<Budget_category> budget_categoryList = budgetRepository.findBudget_category(member, budget);
//
//
//        assertEquals(Budget_status.ongoing, budget.getBudget_status());
//        assertEquals(7, budget.getPeriod().getValue());
//        assertEquals(4, budget_categoryList.size());
//        assertEquals(LocalDate.now().plusDays(7), budget.getEnd_day());

    }
    @Test
    public void 한달예산설정(){

        BudgetDTO budgetDTO = new BudgetDTO(memberId,500000,Budget_period.month.getValue(), categoryList);
        budgetService.setNewBudget(budgetDTO);

        Budget budget = budgetRepository.findOne(member);
        List<Budget_category> budget_categoryList = budgetRepository.findBudget_category(member, budget);

        assertEquals(Budget_status.ongoing, budget.getBudget_status());
        assertEquals(30, budget.getPeriod().getValue());
        assertEquals(4, budget_categoryList.size());
        assertEquals(LocalDate.now().plusDays(30), budget.getEnd_day());

    }


    @Test
    public void 이전예산종료(){

        Budget budget = new Budget(member,500000,500000,Budget_period.two_week,Budget_status.ongoing,
                LocalDate.now().minusDays(10),LocalDate.now().plusDays(4));

        budgetRepository.save(budget);

        BudgetDTO budgetDTO = new BudgetDTO(memberId,500000,Budget_period.two_week.getValue(), categoryList);
        budgetService.setNewBudget(budgetDTO);

        assertEquals(LocalDate.now().minusDays(1), budget.getEnd_day());
        assertEquals(Budget_status.end, budget.getBudget_status());

    }


    @Test
    public void 예산지출입력() {

        Budget budget = new Budget(member,500000,500000,Budget_period.two_week,Budget_status.ongoing,
                LocalDate.now().minusDays(10),LocalDate.now().plusDays(4));

        budgetRepository.save(budget);

        ExpenseRecordDTO recordDTO = new ExpenseRecordDTO(memberId,1L,20000,"샤브샤브","홍대 로운",Money_type.expense,LocalDate.now());
        budgetService.saveRecord(recordDTO);


        assertEquals(480000,budget.getTotal_budget());


    }




}
