package com.hyun.musicmark.memo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class MusicMarkFolerType {

    @Enumerated
    private FolderType floder_type;
    private String folder_name;
    private String foloder_desc;

    public static MusicMarkFolerType setting() {
        return MusicMarkFolerType.builder()
                .floder_type(FolderType.DEFUALT)
                .folder_name("기본 폴더")
                .build();
    }
}
