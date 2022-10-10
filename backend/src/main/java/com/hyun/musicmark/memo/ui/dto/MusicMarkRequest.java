package com.hyun.musicmark.memo.ui.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class MusicMarkRequest {
    private String track_id;
    private String mark_info;
    private String memo;
    private String album_url;
    private String music_name;
    private String singer;
}
