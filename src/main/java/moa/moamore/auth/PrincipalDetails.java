package moa.moamore.auth;

//Security Session => Authentication 타입의 객체(session 정보 들어감) -> UserDetail

import lombok.RequiredArgsConstructor;
import moa.moamore.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;


//로그인 요청이 들어오면, 시큐리티가 낚아채서 로그인을 시킨다.
// 로그인이 완료되면 시큐리티 session 객체를 만들어주는데,(키값 : Security ContextHolder)
// session 오브젝트의 타입은 Authentication 이어야 하고
// 그 안에 유저 정보가 있어야 한다.
// 유저 오브젝트의 타입은 userDeatails 타입 객체여야 함

//Security Session => Authentication => UserDetails(PricipalDetails)

public class PrincipalDetails implements UserDetails {


    private Member member;

    public PrincipalDetails(Member member) {
        this.member = member;
    }

    //해당 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
