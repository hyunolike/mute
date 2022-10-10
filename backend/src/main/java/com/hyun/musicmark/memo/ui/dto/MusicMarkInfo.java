package com.hyun.musicmark.memo.ui.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicMarkInfo {
    private Long memo_id;
    private String track_id;
    private String mark_info;
    private String memo;
    private String album_url;
    private String music_name;
    private String singer;
}
