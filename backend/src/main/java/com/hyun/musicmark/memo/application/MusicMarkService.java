package com.hyun.musicmark.memo.application;

import com.hyun.musicmark.memo.domain.MusicMark;
import com.hyun.musicmark.memo.domain.MusicMarkFolerType;
import com.hyun.musicmark.memo.domain.MusicMarkRepository;
import com.hyun.musicmark.memo.ui.dto.*;
import com.hyun.musicmark.user.domain.User;
import com.hyun.musicmark.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicMarkService {
    private final MusicMarkRepository musicMarkRepository;
    private final UserRepository userRepository;

    public void registerMusicMark(MusicMarkRequest musicMarkRequest){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        Optional<User> user = userRepository.findUserByEmail(userDetails.getUsername());

        MusicMark musicMark = MusicMark.builder()
                .track_id(musicMarkRequest.getTrack_id())
                .mark_info(musicMarkRequest.getMark_info())
                .memo(musicMarkRequest.getMemo())
                .album_url(musicMarkRequest.getAlbum_url())
                .music_name(musicMarkRequest.getMusic_name())
                .singer(musicMarkRequest.getSinger())
                .musicMarkFolerType(MusicMarkFolerType.setting())
                .build();

        if(user.isPresent()){
            user.get().addMusicMark(musicMark);
        }

        musicMarkRepository.save(musicMark);
    }

    public void updateMusicMark(Long memoId, MusicMarkRequest musicMarkRequest){
        musicMarkRepository.updateMusicMark(memoId, musicMarkRequest.getMemo(), musicMarkRequest.getMark_info());
    }

    public MusicMarkInfo bringMuteMemo(Long memoId){
        Optional<MusicMark> musicMark = musicMarkRepository.findById(memoId);

        MusicMarkInfo musicMarkInfo = null;

        if(musicMark.isPresent()){
            musicMarkInfo = MusicMarkInfo.builder()
                    .memo_id(musicMark.get().getMemo_id())
                    .mark_info(musicMark.get().getMark_info())
                    .track_id(musicMark.get().getTrack_id())
                    .memo(musicMark.get().getMemo())
                    .music_name(musicMarkInfo.getMusic_name())
                    .singer(musicMarkInfo.getSinger())
                    .build();
        }

        return musicMarkInfo;
    }

    public MusicMarkListInfo bringMuteMemoList(Long userId){
        Optional<User> user = userRepository.findById(userId);

        List<MusicMarkInfo> musicMarkInfo = user.get().getMusicMarks().stream()
                .map(musicMark -> MusicMarkInfo.builder()
                        .memo_id(musicMark.getMemo_id())
                        .album_url(musicMark.getAlbum_url())
                        .mark_info(musicMark.getMark_info())
                        .memo(musicMark.getMemo())
                        .track_id(musicMark.getTrack_id())
                        .singer(musicMark.getSinger())
                        .music_name(musicMark.getMusic_name())
                        .build()).collect(Collectors.toList());

        return MusicMarkListInfo.builder().musicmark_list(musicMarkInfo).build();
    }

    /**
     * 폴더별 그룹핑
     */
    // TODO: 폴더 테이블 분리 및 로직 수정 필요 2022/10/23 일
    public MusicMarkFolderListInfo bringFolderList(Long userId){
        Optional<User> user = userRepository.findById(userId);

        List<FolderInfo> folderInfos = user.get().getMusicMarks().stream()
                .distinct()
                .map(musicMark -> FolderInfo.builder()
                        .folder_name(musicMark.getMusicMarkFolerType().getFolder_name())
                        .folder_type(musicMark.getMusicMarkFolerType().getFloder_type().toString())
                        .folder_desc(musicMark.getMusicMarkFolerType().getFoloder_desc())
                        .build())
                .collect(Collectors.toList());

        return MusicMarkFolderListInfo.builder().folder_list(folderInfos).build();
    }

    /**
     * <h3>양방향 관계에서의 삭제 로직</h3>
     * 1. 먼저, 유저의 뮤직메모 리스트의 해당 아이디 부분을 삭제 <br>
     * 2. 그 다음으로, 유저 레포의 아이디의 부분을 삭제 <br>
     * 3. 이렇게 순차적으로 삭제해야 양방향 관계에서의 삭제 로직이 완성!
     */
    @Transactional
    public void deleteMemo(Long memoId, Long userId){
        Optional<User> user = userRepository.findById(userId);

        user.get().deleteMemo(memoId);

        musicMarkRepository.deleteById(memoId);
    }
}
