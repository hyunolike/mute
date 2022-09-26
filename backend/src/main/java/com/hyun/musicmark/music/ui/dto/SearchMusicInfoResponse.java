package com.hyun.musicmark.music.ui.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SearchMusicInfoResponse {
    private final List<SearchMusicInfo> musics;

    public SearchMusicInfoResponse(List<SearchMusicInfo> musics) {
        this.musics = musics;
    }
}
