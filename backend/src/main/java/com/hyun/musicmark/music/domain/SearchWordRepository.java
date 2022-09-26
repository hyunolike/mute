package com.hyun.musicmark.music.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SearchWordRepository extends JpaRepository<SearchWord, Long> {
    @Transactional
    @Modifying
    @Query("delete from SearchWord s where s.user.userId = :userId")
    void deleteByAll(@Param("userId") Long userId);
}
