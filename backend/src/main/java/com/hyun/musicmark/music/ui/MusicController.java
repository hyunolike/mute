package com.hyun.musicmark.music.ui;

import com.hyun.musicmark.music.application.MusicService;
import com.hyun.musicmark.music.ui.dto.MusicInfo;
import com.hyun.musicmark.music.ui.dto.SearchInfo;
import com.hyun.musicmark.music.ui.dto.SearchMusicInfoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = {"음악 검색 및 정보를 제공하는 API"})
@RestController
@RequestMapping("/api") // TODO: 릴리즈 시 api >> v1 변경
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @ApiOperation(value = "가수 및 노래 검색에 따른 음악 목록을 반환하는 메소드")
    @ApiImplicitParam(name = "article-name", value = "가수, 가사의 일부분, 노래 제목 등 모든 검색 키워드 가능", dataType = "string")
    @GetMapping("/search")
    public SearchMusicInfoResponse searchMusicInfo(@RequestParam(name = "article-name") String articleName) throws IOException {
        return musicService.searchMusicInfo(articleName);
    }

    @ApiOperation(value = "현재 접속한 유저의 검색 기록 리스트를 반환하는 메소드")
    @ApiImplicitParam(name = "user-id", value = "접속한 유저의 primary id 값", dataType = "long")
    @GetMapping("/search/{user-id}")
    public SearchInfo searchMusicHistoryList(@PathVariable(name = "user-id") Long userId){
        return musicService.bringMusicHistoryList(userId);
    }

    @ApiOperation(value = "현재 접속한 유저의 모든 검색 기록을 삭제하는 메소드")
    @ApiImplicitParam(name = "user-id", value = "접속한 유저의 primary id 값", dataType = "long")
    @DeleteMapping("/search/{user-id}")
    public void deleteAllSearchMusicHistory(@PathVariable(name = "user-id") Long userId){
        musicService.deleteAllSearchHistory(userId);
    }

//    @DeleteMapping("/search/{user-id}")
//    public void deleteSearchMusicHistory(){
//
//    }

    @ApiOperation(value = "음악 + 가수의 상세 정보(가사, 발매일 등)를 반환하는 메소드")
    @ApiImplicitParam(name = "article-name", value = "[노래제목명 + 가수] ex.숨 박효신", dataType = "string")
    @GetMapping("/music-info/{article-name}")
    public MusicInfo bringMusicInfo(@PathVariable(name = "article-name") String articleName) throws IOException {
        return musicService.bringMusicInfo(articleName);
    }
}
