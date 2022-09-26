package com.hyun.musicmark.music.ui.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchMusicInfo {
    private  String track_id;
    private  String music_name;
    private  String album_url;
    private  String singer;
}
