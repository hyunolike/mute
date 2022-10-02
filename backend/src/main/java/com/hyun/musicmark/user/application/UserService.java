package com.hyun.musicmark.user.application;

import com.hyun.musicmark.auth.domain.Authority;
import com.hyun.musicmark.auth.domain.Oauth2User;
import com.hyun.musicmark.auth.domain.Oauth2UserRepository;
import com.hyun.musicmark.user.domain.User;
import com.hyun.musicmark.user.domain.UserRepository;
import com.hyun.musicmark.user.ui.dto.MypageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Oauth2UserRepository oauth2UserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(
                ()->new UsernameNotFoundException(username));
    }

    public Optional<User> findUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void addAuthority(Long userId, String authority){
        userRepository.findById(userId).ifPresent(user->{
            Authority newRole = new Authority(user.getUserId(), authority);
            if(user.getAuthorities() == null){
                HashSet<Authority> authorities = new HashSet<>();
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            }else if(!user.getAuthorities().contains(newRole)){
                HashSet<Authority> authorities = new HashSet<>();
                authorities.addAll(user.getAuthorities());
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            }
        });
    }

    public void removeAuthority(Long userId, String authority){
        userRepository.findById(userId).ifPresent(user->{
            if(user.getAuthorities()==null) return;
            Authority targetRole = new Authority(user.getUserId(), authority);
            if(user.getAuthorities().contains(targetRole)){
                user.setAuthorities(
                        user.getAuthorities().stream().filter(auth->!auth.equals(targetRole))
                                .collect(Collectors.toSet())
                );
                save(user);
            }
        });
    }

    public User loadUser(final Oauth2User oauth2User){
        Oauth2User user = oauth2UserRepository.findById(oauth2User.getOauth2UserId()).orElseGet(()->{
            User spUser = new User();
            spUser.setEmail(oauth2User.getEmail());
            spUser.setEnabled(true);
            spUser.setPassword("");
            userRepository.save(spUser);
            addAuthority(spUser.getUserId(), "ROLE_USER");
            oauth2User.setUserId(spUser.getUserId());
            oauth2User.setCreated(LocalDateTime.now());
            return oauth2UserRepository.save(oauth2User);
        });
        return userRepository.findById(user.getUserId()).get();
    }

    // 마이페이지 서비스
    public MypageInfo bringUserInfo(Long userId){
        Optional<User> user = userRepository.findById(userId);

        return MypageInfo.builder()
                .email(user.get().getEmail())
                .musicmark_count(user.get().getMusicMarks().size())
                .build();
    }
}
