package moa.moamore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // 비활성화
        http.authorizeRequests()
                .antMatchers("/member/login", "/member/signup", "/css/**", "/img/**", "/js/**", "/vendor/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/member/login")
                .usernameParameter("memberId")
                .loginProcessingUrl("/member/login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutUrl("/member/logout")
                .logoutSuccessUrl("/member/login");
    }


}
