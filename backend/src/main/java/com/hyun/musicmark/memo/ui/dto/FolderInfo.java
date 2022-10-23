package com.hyun.musicmark.memo.ui.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FolderInfo {
    private String folder_name;
    private int folder_count;
    private String folder_type;
    private String folder_desc;

}
