package moa.moamore.service;

import lombok.RequiredArgsConstructor;
import moa.moamore.domain.Budget_expense;
import moa.moamore.domain.Category;
import moa.moamore.domain.Member;
import moa.moamore.domain.Money_type;
import moa.moamore.dto.Budget_expenseDTO;
import moa.moamore.dto.CategoryDTO;
import moa.moamore.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
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

    public List<CategoryDTO> getExpenseCategories(String memberId) {

        List<Category> categoryList = categoryRepository.findByMemberAndType(Money_type.expense, memberId);

        return Arrays.asList(modelMapper.map(categoryList,CategoryDTO[].class));
    }

    public List<CategoryDTO> findCategoriesByType(String memberId, Money_type money_type) {

        List<Category> categoryList = categoryRepository.findByMemberAndType(money_type, memberId);
        List<CategoryDTO> expenseDTOList = Arrays.asList(modelMapper.map(categoryList,CategoryDTO[].class));

        return expenseDTOList;

    }


    public List<Budget_expenseDTO> findTopExpense(Long budgetId) {
        List<Budget_expense> categoryList = categoryRepository.findTopExpense(budgetId);
        List<Budget_expenseDTO> categoryDTOList = Arrays.asList(modelMapper.map(categoryList,Budget_expenseDTO[].class));
        return categoryDTOList;
    }
}
