package com.hyun.musicmark.music.ui;

import com.hyun.musicmark.music.application.MusicService;
import com.hyun.musicmark.music.ui.dto.MusicInfo;
import com.hyun.musicmark.music.ui.dto.SearchInfo;
import com.hyun.musicmark.music.ui.dto.SearchMusicInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api") // TODO: 릴리즈 시 api >> v1 변경
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/search")
    public SearchMusicInfoResponse searchMusicInfo(@RequestParam(name = "article-name") String articleName) throws IOException {
        return musicService.searchMusicInfo(articleName);
    }

    @GetMapping("/search/{user-id}")
    public SearchInfo searchMusicHistoryList(@PathVariable(name = "user-id") Long userId){
        return musicService.bringMusicHistoryList(userId);
    }

    @DeleteMapping("/search/{user-id}")
    public void deleteSearchMusicHistory(@PathVariable(name = "user-id") Long userId){

    }

    @GetMapping("/music-info/{article-name}")
    public MusicInfo bringMusicInfo(@PathVariable(name = "article-name") String articleName) throws IOException {
        return musicService.bringMusicInfo(articleName);
    }
}
