package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
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
                .requestMatchers("/","/login","join","joinProc").permitAll()
                .requestMatchers("/").hasRole("A")
                .requestMatchers("/manager").hasRole("B")
                .requestMatchers("/admin").hasAnyRole("C")
                .anyRequest().authenticated()
        );


        http.formLogin((auth) -> auth.loginPage("/login") //login페이지경로 설정
                .loginProcessingUrl("/loginProc")//프론트에서 로그인 submit 전송 타켓 url 시큐리티가 로그인 처리할수있는
                .permitAll()); //이경로에 아무나 들어올수있게 설정

        //csrf 사이트 위변조 방지
        //post요청시 csrf토큰도 같이 보내주어야 로그인이 진행됨
        //csrf란 요청을 위조하여 사용자가 원하지 않아도 서버측으로 특정 요청을 강제로 보내는 방식이다.
        //(회원정보변경 ,게시글crud를 사용자모르게 요청)

//        http.csrf((auth) -> auth.disable());
//          csrf를 enable 시켜주려면 post시 csrf토큰도 같이 반환해줘야함

        //다중로그인 구현
        http.sessionManagement((auth)->auth
                .maximumSessions(1) //하나의 아이디에대한 다중로그인 허용개수
                .maxSessionsPreventsLogin(true)); // 로그인개수를 초과하였을경우 처리방법 true =차단 false 기존세션 하나삭제


        //세션고정보호
        http.sessionManagement((auth) -> auth
                .sessionFixation().changeSessionId()); //세션 로그인시 동일한 세션에대한 쿠키 id 변경



        return http.build();
    }

    //시큐리티 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }


    //요거해줘야 get방식에서도 logout가능함 csrf때문에 post로 로그인했으면 post로 로그아웃을 해야만했음
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.logout((auth) -> auth.logoutUrl("/logout")
                .logoutSuccessUrl("/"));
        return http.build();
    }


    //권한 계층 등급설정
    @Bean
    public RoleHierarchy roleHierarchy(){
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("C").implies("B") //C>B
                .role("B").implies("A") //B>A
                .build();
    }


}
