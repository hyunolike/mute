package com.hyun.musicmark.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO: 유저 정보 가져오는 방법 로직 2안 추가 예정 22.10.01
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MuteUser {
    private Long id;

    private long getId(){
        // TODO: 검증 로직 추가 예정
        return id;
    }
}
