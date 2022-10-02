package com.hyun.musicmark.user.ui.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MypageInfo {
    private String email;
    private int musicmark_count;
}
