package moa.moamore.service;

import lombok.RequiredArgsConstructor;
import moa.moamore.domain.*;
import moa.moamore.repository.BudgetRepository;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final BudgetRepository budgetRepository;

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

        Member member = memberRepository.findOne(memberId);
        return categoryRepository.findByMemberAndType(Money_type.expense, member);

    }

    public List<Category> findCategoriesByType(String memberId, Money_type money_type) {

        Member member = memberRepository.findOne(memberId);
        return categoryRepository.findByMemberAndType(money_type, member);

    }

    public void deleteCategory(Long id) {

        Category category = categoryRepository.findOne(id);
        category.updateUseYn();
        categoryRepository.delete(category);

    }


    public List<Budget_expense> findTopExpense(Long budgetId) {
        Budget budget = budgetRepository.findOne(budgetId);
        return categoryRepository.findTopExpense(budget);
    }
}
