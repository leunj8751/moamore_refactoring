package moa.moamore.controller;


import lombok.RequiredArgsConstructor;
import moa.moamore.domain.Member;
import moa.moamore.dto.MemberDTO;
import moa.moamore.repository.MemberRepository;
import moa.moamore.service.MemberService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MemberRepository memberRepository;

    @GetMapping("/member/signup")
    public String signupForm(Model model){

        if (isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("memberDTO",new MemberDTO());

        return "member/signupForm";
    }

    @PostMapping("/member/signup")
    public String signup(@Valid MemberDTO memberDTO, BindingResult result){

        String rawPassword = memberDTO.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        Member member = new Member(memberDTO.getMemberId(), encPassword, memberDTO.getNickname());
        memberService.join(member);

        return "redirect:/member/login";

    }

    @GetMapping("/member/login")
    public String loginForm(Model model){

        if (isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("memberDTO",new MemberDTO());

        return "member/loginForm";
    }


    public boolean isAuthenticated() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
