package moa.moamore.auth;

import lombok.RequiredArgsConstructor;
import moa.moamore.domain.Member;
import moa.moamore.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 시큐리티 설정대로 로그인 요청이 오면 UserDetailsService 타입으로 ioc 되어 있는 loadUserByUsername가 실행됨


@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        Member member = memberRepository.findOne(memberId);

        if(member != null){
            return new PrincipalDetails(member);
        }

        return null;
    }
}
