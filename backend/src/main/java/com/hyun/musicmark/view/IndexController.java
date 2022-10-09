package com.hyun.musicmark.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

    @GetMapping("/")
    public String testPage(){
        return "index";
    }

    @GetMapping("/home")
    public String mainPage(Principal principal){
        return principal != null ? "index" : "/auth/login";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/auth/login";
    }
}
