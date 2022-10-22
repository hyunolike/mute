package com.hyun.musicmark.memo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MusicMarkRepository extends JpaRepository<MusicMark, Long> {

    @Transactional
    @Modifying
    @Query("update MusicMark m set m.mark_info = :mark_info, m.memo = :memo where m.memo_id = :id")
    void updateMusicMark(@Param("id") Long memoId, String memo, String mark_info);

    @Query("select count(m.track_id) from MusicMark m where m.user.userId = :user_id")
    Long bringCountMusicMark(@Param("user_id") Long userId);
}
