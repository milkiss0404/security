package com.example.security.controller;

import com.example.security.dto.JoinDTO;
import com.example.security.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class JoinController {

    private final JoinService joinService;
    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProc(JoinDTO joinDTO) {
        System.out.println("joinDTO = " + joinDTO.getUsername());
        System.out.println("joinDTO = " + joinDTO.getPassword());

        joinService.joinProcess(joinDTO);

        return "redirect:/login";
    }
}
