package moa.moamore.service;

import lombok.RequiredArgsConstructor;
import moa.moamore.domain.Category;
import moa.moamore.domain.Member;
import moa.moamore.domain.Money_type;
import moa.moamore.repository.CategoryRepository;
import moa.moamore.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public void setting_category(Member member){
        categoryRepository.save(new Category(member,"월급", Money_type.income));

        categoryRepository.save(new Category(member,"식비", Money_type.expense));
        categoryRepository.save(new Category(member,"교통/차량", Money_type.expense));
        categoryRepository.save(new Category(member,"주거/통신", Money_type.expense));
        categoryRepository.save(new Category(member,"건강", Money_type.expense));
        categoryRepository.save(new Category(member,"교육", Money_type.expense));
        categoryRepository.save(new Category(member,"패션/미용", Money_type.expense));
    }

    public List<Category> getExpenseCategories(String memberId){

        Member member = memberRepository.findOne(memberId);
        return categoryRepository.findByMemberAndType(Money_type.expense,member);

    }

}
