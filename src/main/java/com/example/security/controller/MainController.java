package com.example.security.controller;

import com.example.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main(Model model) {
        //원래는 서비스단에올리면좋음 세션아이디 랑 권한 구하는거임
        //사용자가 로그인을 진행한뒤 사용자정보는 securityContextHolder에 의해서 서버세션에 관리된다.
        //세션아이디는 사용자에게 쿠키로반환된다.
        //이떄 세션에관해 세션의 소멸시간, 아이디당 세션생성개수를 설정할수있다.
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        //role을 구하는방법임
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        //여기까지

        model.addAttribute("id", id);
        model.addAttribute("role", role);



        return "main";
    }
}
