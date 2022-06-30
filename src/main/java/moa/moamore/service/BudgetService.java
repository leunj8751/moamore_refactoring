package moa.moamore.service;


import lombok.RequiredArgsConstructor;
import moa.moamore.domain.*;
import moa.moamore.dto.BudgetDTO;
import moa.moamore.dto.CategoryDTO;
import moa.moamore.dto.ExpenseRecordDTO;
import moa.moamore.repository.BudgetRepository;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.repository.MemberRepository;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final BudgetRepository budgetRepository;


    public void setNewBudget(BudgetDTO budgetDTO) { // budgetDTO

        Member member = memberRepository.findOne(budgetDTO.getMemberId());

        Budget budget = Budget.createBudget(member, budgetDTO.getTotal_budget(), Budget_period.valueOf(budgetDTO.getPeriod()));

        //budget이 존재하면 이전 버짓은 end로 끝나야 함
        Budget findBudget = budgetRepository.findBudget(LocalDate.now());
        if (findBudget != null) {
            findBudget.endBudget();
            budgetRepository.save(findBudget);
        }

        budgetRepository.save(budget);

        List<CategoryDTO> categoryList = budgetDTO.getCategoryList();
        List<Budget_category> budget_categoryList = new ArrayList<>();

        for (CategoryDTO c : categoryList) {
            Category category = categoryRepository.findOne(c.getCategory_id());
            if (c.getAmount() != 0) {
                budget_categoryList.add(new Budget_category(category, budget, member, c.getAmount(), c.getAmount()));
            }
        }

        budgetRepository.saveBudget_categories(budget_categoryList);

    }


    public void saveExpenseRecord(ExpenseRecordDTO expenseRecordDTO) {


        Budget budget = budgetRepository.findBudget(expenseRecordDTO.getDate());
        System.out.println("budget :" + budget);
        Category category = categoryRepository.findOne(expenseRecordDTO.getCategoryId());
        Member member = memberRepository.findOne(expenseRecordDTO.getMemberId());

        if (existBudget(expenseRecordDTO)) {
            Budget_category budget_category = budgetRepository.findBudget_category(member, budget, category);

            Budget_expense budget_expense = new Budget_expense(budget, category, expenseRecordDTO.getAmount(), expenseRecordDTO.getGetContent(), expenseRecordDTO.getMemo());

            budget_category.minusAmount(expenseRecordDTO.getAmount());
            budget.minusAmount(expenseRecordDTO.getAmount());
            budgetRepository.saveBudget_expense(budget_expense);
            budgetRepository.save(budget);
            budgetRepository.saveBudget_category(budget_category);

        } else {
            Except_budget except_budget = new Except_budget(member, category, expenseRecordDTO.getAmount(), expenseRecordDTO.getGetContent(), expenseRecordDTO.getMemo());
            budgetRepository.saveExceptBudget(except_budget);

        }

    }

    private boolean existBudget(ExpenseRecordDTO expenseRecordDTO) {

        Budget budget = budgetRepository.findBudget(expenseRecordDTO.getDate());
        if (budget == null) {
            return false;
        }

        Category category = categoryRepository.findOne(expenseRecordDTO.getCategoryId());
        Member member = memberRepository.findOne(expenseRecordDTO.getMemberId());
        Budget_category budget_category = budgetRepository.findBudget_category(member, budget, category);

        if (budget_category.getAmount() >= expenseRecordDTO.getAmount()) {
            return true;
        }

        return false;
    }

    public List<Budget> findBudgetList(String memberId) {

        Member member = memberRepository.findOne(memberId);
        return budgetRepository.findBudgetList(member);
    }

    public List<Budget_expense> findTopExpense(Long budgetId) {
        Budget budget = budgetRepository.findOne(budgetId);

        return budgetRepository.findBudgeTopExpense(budget);

    }

    public List<CategoryDTO> findBudgetExpenseCategories(LocalDate date) {
        Budget budget = budgetRepository.findBudget(date);
        System.out.println("budgetId :" + budget.getId());
        List<Budget_category> budget_categoryList = budgetRepository.findBudget_category(budget);
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for(Budget_category c : budget_categoryList){
            categoryDTOList.add(new CategoryDTO(c.getCategory().getId(), c.getCategory().getCategory_name()));
        }

        return categoryDTOList;
    }
}
