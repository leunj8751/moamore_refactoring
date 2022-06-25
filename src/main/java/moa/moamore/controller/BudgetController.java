package moa.moamore.controller;

import lombok.RequiredArgsConstructor;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.service.CategoryService;
import org.apache.catalina.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class BudgetController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/budget/setBudget")
    public String setBudgetForm(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        String memberId = (String)session.getAttribute("id");



//        model.addAttribute("memberForm",new MemberForm());// 벨리데이션용 빈 껍데기

        return "budget/setBudget";
    }




}
