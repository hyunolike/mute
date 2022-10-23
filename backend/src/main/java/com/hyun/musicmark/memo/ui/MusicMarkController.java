package com.hyun.musicmark.memo.ui;

import com.hyun.musicmark.memo.application.MusicMarkService;
import com.hyun.musicmark.memo.ui.dto.*;
import com.hyun.musicmark.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"[Mute] 메모 API"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MusicMarkController {

    private final MusicMarkService musicMarkService;

    @ApiOperation(value = "최초 뮤직마크(메모) 등록 메소드")
    @PostMapping("/musicmark")
    public void registerMusicmark(@RequestBody MusicMarkRequest markUpdateRequest){
        musicMarkService.registerMusicMark(markUpdateRequest);
    }

    @ApiOperation(value = "뮤직마크(메모) 수정 메소드")
    @PutMapping("/musicmark/{memo-id}")
    public void reviseMusicmark(@PathVariable(name = "memo-id") Long memoId, @RequestBody MusicMarkRequest markUpdateRequest){
        musicMarkService.updateMusicMark(memoId, markUpdateRequest);
    }

    @ApiOperation(value = "특정 뮤직마크(메모) 삭제 메소드")
    @DeleteMapping("/musicmark/{memo-id}")
    public void deleteMusicmark(@PathVariable(name = "memo-id") Long memoId, @AuthenticationPrincipal User user){
        musicMarkService.deleteMemo(memoId, user.getUserId());
    }

    @ApiOperation(value = "현재 접속한 유저의 뮤직마크(메모) 리스트 목록 반환하는 메소드")
    @GetMapping("/musicmark")
    public MusicMarkListInfo bringListMusicmark(@AuthenticationPrincipal User user){
        return musicMarkService.bringMuteMemoList(user.getUserId());
    }

    @ApiOperation(value = "특정 뮤직마크(메모) 세부 정보 반환하는 메소드")
    @GetMapping("/musicmark/{memo-id}")
    public MusicMarkInfo bringMusicmark(@PathVariable(name = "memo-id") Long memoId){
        return musicMarkService.bringMuteMemo(memoId);
    }

    @ApiOperation(value = "현재 접속한 유저의 뮤직마크(폴더 정보) 리스트 목록 반환하는 메소드")
    @GetMapping("/musicmark/folder")
    public MusicMarkFolderListInfo bringFolderInfo(@AuthenticationPrincipal User user){
        return musicMarkService.bringFolderList(user.getUserId());
    }
}
