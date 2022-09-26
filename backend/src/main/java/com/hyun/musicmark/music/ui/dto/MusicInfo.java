package com.hyun.musicmark.music.ui.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicInfo {
    private String artist_name;
    private String album_name;
    private String release_date;
    private String genre;
    private String composition;
    private String lyricist;
    private String arranger;
    private String lyrics_data;
}
