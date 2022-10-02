package com.hyun.musicmark.memo.ui.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MusicMarkListInfo {
    private List<MusicMarkInfo> musicmark_list;
}
