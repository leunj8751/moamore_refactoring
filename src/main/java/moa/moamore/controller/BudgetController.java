package moa.moamore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moa.moamore.domain.Money_type;
import moa.moamore.dto.BudgetDTO;
import moa.moamore.dto.Budget_expenseDTO;
import moa.moamore.dto.CategoryDTO;
import moa.moamore.dto.ExpenseRecordDTO;
import moa.moamore.service.BudgetService;
import moa.moamore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BudgetController {

    private final CategoryService categoryService;
    private final BudgetService budgetService;
    private final ModelMapper modelMapper;

    @GetMapping("/budget/{memberId}/new")
    public String setForm(@PathVariable("memberId") String memberId, Model model) {

        List<CategoryDTO> categoryList = categoryService.getExpenseCategories(memberId);

        BudgetDTO budgetDTO = new BudgetDTO(memberId,categoryList);

        model.addAttribute("budgetDTO", budgetDTO);

        return "budget/setBudget";
    }


    @PostMapping("/budget/new")
    public String setBudget(@ModelAttribute BudgetDTO budgetDTO) {

        budgetService.setNewBudget(budgetDTO);

        return "redirect:/";
    }

    @GetMapping("/budget/record")
    public String recordForm(Model model) {

        return "budget/moneyRecord";
    }
    @GetMapping("/budget/{memberId}/recordForm")
    public String recordForm(@PathVariable("memberId") String memberId, Model model) {

        List<CategoryDTO> expenseList = categoryService.findCategoriesByType(memberId, Money_type.expense);
        List<CategoryDTO> incomeList = categoryService.findCategoriesByType(memberId, Money_type.income);

        model.addAttribute("expenseList", expenseList);
        model.addAttribute("incomeList", incomeList);

        return "budget/recordForm";
    }


    @PostMapping("/budget/record")
    public String record(ExpenseRecordDTO expenseRecordDTO) {

        budgetService.saveRecord(expenseRecordDTO);

        return "redirect:/budget/"+expenseRecordDTO.getMemberId()+"/recordForm";
    }


    @GetMapping("/budget/{memberId}/report")
    public String budgetReport(@PathVariable("memberId") String memberId, Model model) {

        List<BudgetDTO> budgetDTOList = budgetService.findBudgetList(memberId);
        model.addAttribute("budgetList", budgetDTOList);

        return "/budget/report";
    }

    @GetMapping("/budget/reportContent")
    public String reportContentForm(@RequestParam("budgetId") Long budgetId, Model model) {

        List<Budget_expenseDTO> expenseList = budgetService.findTopExpense(budgetId);
        List<Budget_expenseDTO> categoryList = categoryService.findTopExpense(budgetId);


        model.addAttribute("expenseList", expenseList);
        model.addAttribute("categoryList", categoryList);

        return "/budget/reportContent";
    }

    @GetMapping("/budget/moneyRecordContent")
    public String moneyLogForm(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate date,
           @RequestParam("content") String content ,
           @RequestParam("record_type") String record_type,
                               Model model) {

        List<Budget_expenseDTO> recordList;

        if(record_type.equals("except")){
            recordList = budgetService.findExceptBudgetList(date,content);
        }else{
            recordList = budgetService.findBudgetExpenseList(date,content);
        }

        model.addAttribute("recordList", recordList);

        return "/budget/moneyRecordContent";
    }

}
