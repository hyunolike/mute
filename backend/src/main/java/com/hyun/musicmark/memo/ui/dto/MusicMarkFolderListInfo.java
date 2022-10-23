package com.hyun.musicmark.memo.ui.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MusicMarkFolderListInfo {
    private List<FolderInfo> folder_list;
}
