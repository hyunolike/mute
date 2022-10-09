package com.hyun.musicmark.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String splashPage(){
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/auth/login";
    }
}
