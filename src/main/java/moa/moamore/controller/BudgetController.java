package moa.moamore.controller;

import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import moa.moamore.auth.PrincipalDetails;
import moa.moamore.domain.*;
import moa.moamore.dto.BudgetDTO;
import moa.moamore.dto.CategoryDTO;
import moa.moamore.dto.ExpenseRecordDTO;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.repository.MemberRepository;
import moa.moamore.service.BudgetService;
import moa.moamore.service.CategoryService;
import org.apache.catalina.Session;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;


@Controller
@RequiredArgsConstructor
public class BudgetController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final BudgetService budgetService;


    @GetMapping("/budget/setBudget")
    public String setBudgetForm(Model model, Authentication authentication) {

        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        String memberId = userDetails.getUsername();

        List<Category> categoryList = categoryService.getExpenseCategories(memberId);
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for (Category c : categoryList) {
            categoryDTOList.add(new CategoryDTO(c.getId(), c.getCategory_name()));
        }

        BudgetDTO budgetDTO = new BudgetDTO();
        budgetDTO.setCategoryList(categoryDTOList);
        budgetDTO.setMemberId(memberId);

        model.addAttribute("budgetDTO", budgetDTO);

        return "budget/setBudget";
    }


    @PostMapping("/budget/setBudget")
    public String setBudget(@ModelAttribute BudgetDTO budgetDTO) {

        budgetService.setNewBudget(budgetDTO);

        return "redirect:/";
    }

    @GetMapping("/budget/moneyRecord")
    public String moneyRecordForm(Model model) {

        Budget_expense budget_expense = new Budget_expense();

        model.addAttribute("budget_expense", budget_expense);

        return "budget/moneyRecord";
    }

    @GetMapping("/budget/recordForm")
    public String recordForm(Model model, Authentication authentication) {

        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        String memberId = userDetails.getUsername();

        List<Category> expenseList = categoryService.findCategoriesByType(memberId, Money_type.expense);
        List<Category> incomeList = categoryService.findCategoriesByType(memberId, Money_type.income);
        Budget_expense budget_expense = new Budget_expense();

        model.addAttribute("expenseList", expenseList);
        model.addAttribute("incomeList", incomeList);
        model.addAttribute("budget_expense", budget_expense);

        return "budget/recordForm";
    }


    @PostMapping("/budget/expenseRecord")
    public String expenseRecord(ExpenseRecordDTO expenseRecordDTO) {

        budgetService.saveExpenseRecord(expenseRecordDTO);

        return "redirect:/budget/moneyRecord";
    }


    @GetMapping("/budget/report")
    public String budgetReport(Model model, Authentication authentication) {

        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        String memberId = userDetails.getUsername();

        List<Budget> budgetList = budgetService.findBudgetList(memberId);

        model.addAttribute("budgetList", budgetList);

        return "/budget/report";
    }

    @GetMapping("/budget/reportContent")
    public String budgetReportContent(@RequestParam("budgetId") Long budgetId, Model model) {

        List<Budget_expense> expenseList = budgetService.findTopExpense(budgetId);
        List<Budget_expense> categoryList = categoryService.findTopExpense(budgetId);

        model.addAttribute("expenseList", expenseList);
        model.addAttribute("categoryList", categoryList);

        return "/budget/reportContent";
    }

}
