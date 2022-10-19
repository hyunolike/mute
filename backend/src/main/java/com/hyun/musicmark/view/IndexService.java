package com.hyun.musicmark.view;

import com.hyun.musicmark.memo.domain.MusicMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndexService {
    private final MusicMarkRepository musicMarkRepository;

    public Long bringCountMusicmark(Long userId){
        return musicMarkRepository.bringCountMusicMark(userId);
    }
}
