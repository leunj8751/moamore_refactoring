package moa.moamore.service;

import lombok.RequiredArgsConstructor;
import moa.moamore.domain.*;
import moa.moamore.repository.BudgetRepository;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final BudgetRepository budgetRepository;

    @Transactional
    public void setting_category(Member member) {
        categoryRepository.save(new Category(member, "월급", Money_type.income));

        categoryRepository.save(new Category(member, "식비", Money_type.expense));
        categoryRepository.save(new Category(member, "교통/차량", Money_type.expense));
        categoryRepository.save(new Category(member, "주거/통신", Money_type.expense));
        categoryRepository.save(new Category(member, "건강", Money_type.expense));
        categoryRepository.save(new Category(member, "교육", Money_type.expense));
        categoryRepository.save(new Category(member, "패션/미용", Money_type.expense));

    }

    public List<Category> getExpenseCategories(String memberId) {
        return categoryRepository.findByMemberAndType(Money_type.expense, memberId);
    }

    public List<Category> findCategoriesByType(String memberId, Money_type money_type) {
        return categoryRepository.findByMemberAndType(money_type, memberId);

    }


    public List<Budget_expense> findTopExpense(Long budgetId) {
        return categoryRepository.findTopExpense(budgetId);
    }
}
