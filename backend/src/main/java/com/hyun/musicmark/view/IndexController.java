package com.hyun.musicmark.view;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

    @GetMapping("/")
    public String testPage(){
        return "index";
    }

    /**
     * <h2>페이지 권한 설정 방법1</h2>
     */
    @GetMapping("/home")
    public String mainPage(Principal principal){
        return principal != null ? "index" : "/auth/login";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/auth/login";
    }

    /**
     * <h2>페이지 권한 설정 방법2</h2>
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public String bringSearchResultPage(){
        return "/search/search-result";
    }
}
