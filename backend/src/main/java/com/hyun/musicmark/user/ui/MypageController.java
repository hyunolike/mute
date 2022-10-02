package com.hyun.musicmark.user.ui;

import com.hyun.musicmark.user.application.UserService;
import com.hyun.musicmark.user.domain.User;
import com.hyun.musicmark.user.ui.dto.MypageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"[Mute] 마이페이지 정보 제공하는 API"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MypageController {

    private final UserService userService;

    @ApiOperation(value = "마이페이지 정보 반환하는 메소드")
    @GetMapping("/mypage")
    public MypageInfo bringUserInfo(@AuthenticationPrincipal User user){
        return userService.bringUserInfo(user.getUserId());
    }
}
