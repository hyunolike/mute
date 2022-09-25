package com.hyun.musicmark.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_authority")
@IdClass(Authority.class)
public class Authority implements GrantedAuthority {
    @Id
    @Column(name="user_id")
    private Long userId;

    @Id
    private String authority;
}
