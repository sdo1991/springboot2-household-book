package com.dongoh.project.springboot.config.auth;

import com.dongoh.project.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;

@RequiredArgsConstructor
@EnableWebSecurity//spring security 관련 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final HttpFirewall httpFirewall;
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.httpFirewall(httpFirewall);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()//h2-console이용위해 해당옵션 disable
            .and().authorizeRequests()//url별 권한관리 시작, antmatchers쑤기위해 선언필요
                .antMatchers("/","/layout/**","/css/**","/image/**","/js/**","/h2-console/**","/login_page/**","/register_page/**").permitAll()
                .antMatchers("/main/**","/tag_page/**","/follower_page/**","/user_page/**","/my_message/**","/update_user/**").hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                .antMatchers("/api/**").permitAll()
                .antMatchers("/admin_post_page/**","/admin_report_page/**","/admin_notice_page/**").hasRole(Role.ADMIN.name())
                .antMatchers( "/register_google_page/**").hasAnyRole(Role.GUEST.name(),Role.ADMIN.name())
                .antMatchers("/login","/signUp").permitAll()
                .anyRequest().permitAll()

            .and()
                .exceptionHandling().accessDeniedPage("/reject_page")

            .and().formLogin()
                .defaultSuccessUrl("/form_login_success", true)
                .usernameParameter("email")
                .passwordParameter("password")
                .failureUrl("/login_page?error")
                .loginPage("/login_page")
                .loginProcessingUrl("/loginProcess")
                .permitAll()

            .and().oauth2Login()//로그인 기능 설정 시작
                .defaultSuccessUrl("/oauth2_login_success", true)
                .userInfoEndpoint()//로그인 성공 후 사용자 정보 가져오기 설정
                    .userService(customOAuth2UserService);//로그인 성공 후 진행할 UserService 인터페이스구현체 등록

//            .and().loginPage("/login_page");//미인증 상태일시 커스텀 로그인 페이지로 이동
    }
}
