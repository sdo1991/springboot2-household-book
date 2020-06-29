package com.dongoh.project.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@EnableJpaAuditing
@SpringBootApplication//스프링 부트의 자동 설정, 스프링 bean 읽기 생성 자동 설정, 여기서부터 설정을 읽어가므로 프로젝트 최상단에 위치
public class Application {
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);//내장 WAS 실행
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }
}
