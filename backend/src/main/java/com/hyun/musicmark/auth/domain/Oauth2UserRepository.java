package com.hyun.musicmark.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Oauth2UserRepository extends JpaRepository<Oauth2User, String> {
}
