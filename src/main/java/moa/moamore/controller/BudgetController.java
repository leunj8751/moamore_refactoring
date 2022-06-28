package moa.moamore.controller;

import lombok.RequiredArgsConstructor;
import moa.moamore.auth.PrincipalDetails;
import moa.moamore.domain.Budget;
import moa.moamore.domain.Category;
import moa.moamore.domain.Member;
import moa.moamore.domain.Money_type;
import moa.moamore.dto.BudgetDTO;
import moa.moamore.dto.CategoryDTO;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.service.BudgetService;
import moa.moamore.service.CategoryService;
import org.apache.catalina.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BudgetController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final BudgetService budgetService;


    @GetMapping("/budget/setBudget")
    public String setBudgetForm(Model model, Authentication authentication){

        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        String memberId = userDetails.getUsername();

        List<Category> categoryList = categoryService.getExpenseCategories(memberId);
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for(Category c : categoryList){
            categoryDTOList.add(new CategoryDTO(c.getId(), c.getCategory_name()));
        }

        BudgetDTO budgetDTO = new BudgetDTO();
        budgetDTO.setCategoryList(categoryDTOList);
        budgetDTO.setMemberId(memberId);

        model.addAttribute("budgetDTO",budgetDTO);

        return "budget/setBudget";
    }


    @PostMapping("/budget/setBudget")
    public String setBudget(@ModelAttribute BudgetDTO budgetDTO){

        budgetService.setNewBudget(budgetDTO);

        return "redirect:/";
    }


}
