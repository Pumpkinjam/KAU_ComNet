package com.comnet.CNProject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@Controller
@RequiredArgsConstructor
public class ManageController {
    private final MemberRepository memberRepository;

    @GetMapping("/manage")
    public String showManagePage() {
        return "game.html";
    }

    @PostMapping("/join")
    @ResponseBody
    public String join(@RequestBody Member member) {
        if (memberRepository.isEmpty()) { //아무도 없으면 출제자
            member.setRole("host");
        }
        else{
            member.setRole("client");
        }
        memberRepository.addUser(member);
        return "hello";
    }
}
