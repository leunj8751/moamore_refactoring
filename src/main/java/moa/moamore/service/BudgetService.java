package moa.moamore.service;


import lombok.RequiredArgsConstructor;
import moa.moamore.domain.*;
import moa.moamore.dto.BudgetDTO;
import moa.moamore.dto.CategoryDTO;
import moa.moamore.repository.BudgetRepository;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final BudgetRepository budgetRepository;


    public void setNewBudget(BudgetDTO budgetDTO){ // budgetDTO

        Member member = memberRepository.findOne(budgetDTO.getMemberId());

        Budget budget = Budget.createBudget(member,budgetDTO.getTotal_budget(),Budget_period.valueOf(budgetDTO.getPeriod()));

        budgetRepository.save(budget);

        List<CategoryDTO> categoryList = budgetDTO.getCategoryList();
        List<Budget_category> budget_categoryList = new ArrayList<>();

        for(CategoryDTO c: categoryList){
            Category category = categoryRepository.findOne(c.getCategory_id());
            if(c.getAmount() != 0){
                budget_categoryList.add(new Budget_category(category,budget,member,c.getAmount(),c.getAmount()));
            }
        }

        budgetRepository.saveBudget_category(budget_categoryList);

    }



}
