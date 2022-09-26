package com.hyun.musicmark.music.ui.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchInfo {
    private List<String> searchWords;
}
