package com.hyun.musicmark.view;

import com.hyun.musicmark.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final IndexService indexService;

    @GetMapping("/")
    public String testPage(){
        return "index";
    }

    /**
     * <h2>페이지 권한 설정 방법1</h2>
     */
    @GetMapping("/home")
    public String mainPage(Principal principal){
        return principal != null ? "index" : "auth/login";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    /**
     * <h2>페이지 권한 설정 방법2</h2>
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public String bringSearchResultPage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("userId", user.getUserId());

        return "search/search-result";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/music-info")
    public String bringMusicInfoResultPage(){
        return "search/search-detail-result";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my-page")
    public String myPage(@AuthenticationPrincipal User user, Model model){
        Long count = indexService.bringCountMusicmark(user.getUserId());

        model.addAttribute("email", user.getEmail());
        model.addAttribute("count", count);

        return "setting/mypage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/music-mark")
    public String musicMarkPage(){
        return "memo/musicmark";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/music-mark-folder")
    public String saveMusicMarkPage(){
        return "memo/musicmark-folder";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/memo")
    public String memoPage() {
        return "memo/memo";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/folder")
    public String folderPage() {
        return "memo/folder";
    }
}
