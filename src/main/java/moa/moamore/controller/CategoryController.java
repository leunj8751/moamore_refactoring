package moa.moamore.controller;

import lombok.RequiredArgsConstructor;
import moa.moamore.domain.Category;
import moa.moamore.domain.Member;
import moa.moamore.domain.Money_type;
import moa.moamore.dto.BudgetDTO;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.repository.MemberRepository;
import moa.moamore.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    private final MemberRepository memberRepository;

    @GetMapping("/category/{memberId}/list")
    public String categoryList(@PathVariable("memberId") String memberId, Model model) {


        List<Category> expenseList = categoryService.findCategoriesByType(memberId, Money_type.expense);
        List<Category> incomeList = categoryService.findCategoriesByType(memberId, Money_type.income);
        Category category = new Category();

        model.addAttribute("expenseList", expenseList);
        model.addAttribute("incomeList", incomeList);
        model.addAttribute("category", category);
        model.addAttribute("money_types", Money_type.values());


        return "category/setCategory";
    }

    @PostMapping("/category/{memberId}/new")
    public String setCategory(@PathVariable("memberId") String memberId, @ModelAttribute Category category) {

        System.out.println("cateogryInfo :" + category.getCategory_name());
        Member member = memberRepository.findOne(memberId);
        category.setMember(member);

        categoryRepository.save(category);

        return "redirect:/category/{memberId}/list";
    }

    @PostMapping("/category/{categoryId}/delete")
    public String deleteCategory(@PathVariable("categoryId") String categoryId) {

        categoryService.deleteCategory(Long.valueOf(categoryId));
        return "redirect:/category/{memberId}/list";
    }


}
