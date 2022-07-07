package moa.moamore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moa.moamore.domain.Budget_expense;
import moa.moamore.domain.Category;
import moa.moamore.domain.Except_budget;
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
import java.util.Arrays;
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

        List<Category> categoryList = categoryService.getExpenseCategories(memberId);
        List<CategoryDTO> categoryDTOList = Arrays.asList(modelMapper.map(categoryList,CategoryDTO[].class));
        BudgetDTO budgetDTO = new BudgetDTO(memberId,categoryDTOList);

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

        List<Category> expenseList = categoryService.findCategoriesByType(memberId, Money_type.expense);
        List<CategoryDTO> expenseDTOList = Arrays.asList(modelMapper.map(expenseList,CategoryDTO[].class));

        List<Category> incomeList = categoryService.findCategoriesByType(memberId, Money_type.income);
        List<CategoryDTO> incomeDTOList = Arrays.asList(modelMapper.map(incomeList,CategoryDTO[].class));

        model.addAttribute("expenseList", expenseDTOList);
        model.addAttribute("incomeList", incomeDTOList);

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

        List<Budget_expense> expenseList = budgetService.findTopExpense(budgetId);
        List<Budget_expenseDTO> expenseDTOList = Arrays.asList(modelMapper.map(expenseList,Budget_expenseDTO[].class));

        List<Budget_expense> categoryList = categoryService.findTopExpense(budgetId);
        List<Budget_expenseDTO> categoryDTOList = Arrays.asList(modelMapper.map(categoryList,Budget_expenseDTO[].class));

        model.addAttribute("expenseList", expenseDTOList);
        model.addAttribute("categoryList", categoryDTOList);

        return "/budget/reportContent";
    }

    @GetMapping("/budget/moneyRecordContent")
    public String moneyLogForm(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate date,
           @RequestParam("content") String content ,
           @RequestParam("record_type") String record_type,
                               Model model) {

        List<Budget_expenseDTO> recordDTOList;

        if(record_type.equals("except")){
            List<Except_budget> recordList = budgetService.findExceptBudgetList(date,content);
            recordDTOList = Arrays.asList(modelMapper.map(recordList,Budget_expenseDTO[].class));
        }else{
            List<Budget_expense> recordList = budgetService.findBudgetExpenseList(date,content);
            recordDTOList = Arrays.asList(modelMapper.map(recordList,Budget_expenseDTO[].class));
        }

        model.addAttribute("recordList", recordDTOList);

        return "/budget/moneyRecordContent";
    }

}
