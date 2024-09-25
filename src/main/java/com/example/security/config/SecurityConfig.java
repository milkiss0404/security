package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //스프링 시큐리티에서 관리 받을수있음
public class SecurityConfig {

    //authorizeHttpRequests() 요청에대해 거부 승인 처리
    //.requestMatchers ~ 경로에 작업을 진행할것이다
    //authenticated  -> 로그인을 진행해야지 들어갈수있음 permitAll() 모든 사용자 접근가능
    //.requestMatchers("/admin").hasRole("ADMIN") ADMIN이라는 역할이있어야 접근가능
    //** 는 와일드카드 .anyRequest().authenticated()는 나머지 요청은 로그인이있어야한다
    //무조건 람다식으로 선언해줘야함 스프림 3.1부터 필수로 바뀜
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth)->auth
                .requestMatchers("/","/login").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated()
        );


        http.formLogin((auth) -> auth.loginPage("/login") //login페이지경로 설정
                .loginProcessingUrl("/loginProc")//프론트에서 로그인 submit 전송 타켓 url 시큐리티가 로그인 처리할수있는
                .permitAll()); //이경로에 아무나 들어올수있게 설정

        //csrf 사이트 위변조 방지
        //post요청시 csrf토큰도 같이 보내주어야 로그인이 진행됨

        http.csrf((auth) -> auth.disable());

        return http.build();
    }

    //시큐리티 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
