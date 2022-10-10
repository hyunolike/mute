package com.hyun.musicmark.memo.domain;

import com.hyun.musicmark.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "musicmark")
public class MusicMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memo_id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_musicmark_to_user"))
    private User user;

    private String track_id;

    private String mark_info;
    private String memo;

    @Embedded
    private MusicMarkFolerType musicMarkFolerType;

    private String album_url;

    private String music_name;

    private String singer;
}