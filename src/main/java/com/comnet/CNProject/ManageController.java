package com.comnet.CNProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManageController {

    @GetMapping("/manage")
    public String showManagePage() {
        return "game.html";
    }
}
