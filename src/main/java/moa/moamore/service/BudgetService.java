package moa.moamore.service;


import lombok.RequiredArgsConstructor;
import moa.moamore.domain.*;
import moa.moamore.dto.BudgetDTO;
import moa.moamore.dto.CategoryDTO;
import moa.moamore.dto.ExpenseRecordDTO;
import moa.moamore.repository.BudgetRepository;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BudgetService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final BudgetRepository budgetRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public void setNewBudget(BudgetDTO budgetDTO) {

        Member member = memberRepository.findOne(budgetDTO.getMemberId());

        Budget budget = Budget.createBudget(member, budgetDTO.getTotal_budget(), Budget_period.valueOf(budgetDTO.getPeriod()));

        handleOldBudget();
        budgetRepository.save(budget);

        saveBudgetCategory(budgetDTO.getCategoryList(),budget,member);

    }



    @Transactional
    public void saveRecord(ExpenseRecordDTO recordDTO) {

        Budget budget = budgetRepository.findOne(recordDTO.getDate());
        Category category = categoryRepository.findOne(recordDTO.getCategoryId());

        if (existBudget(recordDTO)) {
            saveBudgetExpense(budget,category,recordDTO);
        } else {
            saveExceptRecord(category,recordDTO);
        }
    }



    private void saveExceptRecord(Category category, ExpenseRecordDTO recordDTO) {

        Member member = memberRepository.findOne(recordDTO.getMemberId());

        Except_budget except_budget =
                new Except_budget(member, category, recordDTO.getAmount(), recordDTO.getContent(), recordDTO.getMemo(),recordDTO.getType());

        budgetRepository.saveExceptBudget(except_budget);
    }

    @Transactional
    private void saveBudgetExpense(Budget budget, Category category,ExpenseRecordDTO recordDTO) {

       Budget_category budget_category = budgetRepository.findBudget_category(budget, category);
       Budget_expense budget_expense =
               new Budget_expense(budget, category, recordDTO.getAmount(), recordDTO.getContent(), recordDTO.getMemo());

        budget_category.minusAmount(budget_expense.getAmount());
        budget.minusAmount(budget_expense.getAmount());

        budgetRepository.saveBudget_expense(budget_expense);

    }
    
    

    public boolean existBudget(ExpenseRecordDTO expenseRecordDTO) {

        Money_type type = expenseRecordDTO.getType();
        Budget budget = budgetRepository.findOne(expenseRecordDTO.getDate());

        Category category = categoryRepository.findOne(expenseRecordDTO.getCategoryId());
        Budget_category budget_category = budgetRepository.findBudget_category(budget, category);

        if(type != null || budget == null || budget_category == null || budget_category.getAmount() < expenseRecordDTO.getAmount()){
            return false;
        }

        return true;
    }

    public List<BudgetDTO> findBudgetList(String memberId) {

        Member member = memberRepository.findOne(memberId);
        List<Budget> budgetList = member.getBudgetList();
        List<BudgetDTO> budgetDTOList = Arrays.asList(modelMapper.map(budgetList,BudgetDTO[].class));


        return budgetDTOList;
    }

    public List<Budget_expense> findTopExpense(Long budgetId) {
        return budgetRepository.findBudgeTopExpense(budgetId);

    }

    public List<CategoryDTO> findBudgetExpenseCategories(LocalDate date) {
        Budget budget = budgetRepository.findOne(date);
        List<Budget_category> budget_categoryList = budgetRepository.findBudget_category(budget);
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for(Budget_category c : budget_categoryList){
            categoryDTOList.add(new CategoryDTO(c.getCategory().getId(), c.getCategory().getCategory_name()));
        }

        return categoryDTOList;
    }

    public List<Budget_expense> findBudgetExpenseList(LocalDate date,String content) {

        return budgetRepository.findBudgetExpenseList(date,content);
    }

    @Transactional
    public void handleOldBudget(){
        Budget findBudget = budgetRepository.findOne(LocalDate.now());
        if (findBudget != null) {
            findBudget.endBudget();
        }

    }
    @Transactional
    public void saveBudgetCategory(List<CategoryDTO> categoryDTOList,Budget budget,Member member){

        List<Budget_category> budget_categoryList = new ArrayList<>();

        for (CategoryDTO c : categoryDTOList) {
            Category category = categoryRepository.findOne(c.getId());
            if (c.getAmount() != 0) {
                budget_categoryList.add(new Budget_category(category, budget, c.getAmount(), c.getAmount()));
            }
        }

        budgetRepository.saveBudget_categories(budget_categoryList);
    }

    public List<Except_budget> findExceptBudgetList(LocalDate date, String content) {
        return budgetRepository.findExceptBudgetList(date,content);
    }
}
