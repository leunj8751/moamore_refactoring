package moa.moamore.service;


import lombok.RequiredArgsConstructor;
import moa.moamore.domain.Member;
import moa.moamore.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {


    private final MemberRepository memberRepository;
    private final CategoryService categoryService;


    public void join(Member member) {

        checkMemberDuplicate(member);
        memberRepository.save(member);
        categoryService.setting_category(member);

    }

    private void checkMemberDuplicate(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getId());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


}
