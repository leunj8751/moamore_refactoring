package moa.moamore.controller;

import lombok.RequiredArgsConstructor;
import moa.moamore.domain.Category;
import moa.moamore.domain.Member;
import moa.moamore.domain.Money_type;
import moa.moamore.dto.CategoryDTO;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.repository.MemberRepository;
import moa.moamore.service.CategoryService;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;

    @GetMapping("/category/{memberId}/list")
    public String categoryList(@PathVariable("memberId") String memberId, Model model) {


        List<CategoryDTO> expenseList = categoryService.findCategoriesByType(memberId, Money_type.expense);
        List<CategoryDTO> incomeList = categoryService.findCategoriesByType(memberId, Money_type.income);
        CategoryDTO category = new CategoryDTO();

        model.addAttribute("expenseList", expenseList);
        model.addAttribute("incomeList", incomeList);
        model.addAttribute("category", category);
        model.addAttribute("money_types", Money_type.values());


        return "category/setCategory";
    }

    @PostMapping("/category/{memberId}/new")
    public String setCategory(@PathVariable("memberId") String memberId, @ModelAttribute Category category) {

        Member member = memberRepository.findOne(memberId);
        category.setMember(member);

        categoryRepository.save(category);

        return "redirect:/category/{memberId}/list";
    }


}
