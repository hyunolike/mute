package com.hyun.musicmark.music.application;

import com.hyun.musicmark.music.domain.SearchWord;
import com.hyun.musicmark.music.domain.SearchWordRepository;
import com.hyun.musicmark.music.ui.dto.MusicInfo;
import com.hyun.musicmark.music.ui.dto.SearchInfo;
import com.hyun.musicmark.music.ui.dto.SearchMusicInfo;
import com.hyun.musicmark.music.ui.dto.SearchMusicInfoResponse;
import com.hyun.musicmark.user.domain.User;
import com.hyun.musicmark.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * <h3>1. vibe</h3>
 * 가수 검색: https://apis.naver.com/vibeWeb/musicapiweb/v3/search/artist?query= <br>
 * 트랙 검색: https://apis.naver.com/vibeWeb/musicapiweb/v3/search/track?query= <br>
 * 앨범 검색: https://apis.naver.com/vibeWeb/musicapiweb/v3/search/album?query=
 * </p>
 * <p>
 * <h3>2. bugs</h3>
 * 트랙 검색(가사 정보 포함): https://music.bugs.co.kr/track/[트랙id] <br>
 * 앨범 검색: https://music.bugs.co.kr/album/[앨범id]
 * </p>
 */

@Service
@RequiredArgsConstructor
public class MusicService {

    private final UserRepository userRepository;
    private final SearchWordRepository searchWordRepository;

    public SearchMusicInfoResponse searchMusicInfo(String articleName) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        Optional<User> user = userRepository.findUserByEmail(userDetails.getUsername());

        SearchWord searchWord = SearchWord.builder().search_word(articleName).build();
        searchWordRepository.save(user.get().addSearchWord(searchWord));

        if(user.isPresent()){
            user.get().addSearchWord(SearchWord.builder().search_word(articleName).build());
        }

        String url = "https://apis.naver.com/vibeWeb/musicapiweb/v3/search/track?query="
                + articleName
                + "&start=1&display=100&sort=RELEVANCE";

        Document doc = Jsoup.connect(url).userAgent("Chrome").get();
        List<SearchMusicInfo> data = new ArrayList<>();

        for(Element element : doc.select("response > result > tracks > track")) {
            data.add(SearchMusicInfo.builder()
                    .music_name(element.select("trackTitle").text())
                    .track_id(element.select("trackId").text())
                    .album_url(element.select("album > imageUrl").text())
                    .singer(element.select("album > artists > artist > artistName").text())
                    .build());
        }

        SearchMusicInfoResponse musicInfoResponse = new SearchMusicInfoResponse(data);
        return musicInfoResponse;
    }

    /**
     * String articleName: 음악 + 가수명
     */
    public MusicInfo bringMusicInfo(String articleName) throws IOException {
        String requestUrl = "https://music.bugs.co.kr/search/lyrics?q=" + articleName;
        Document requestDoc = Jsoup.connect(requestUrl).get();
        Element rquestElement = requestDoc.select("table[class=list trackList lyrics] > tbody > tr").first();
        String trackId = rquestElement.attr("trackid");
        String albumId = rquestElement.attr("albumid");

        String albumDataUrl = "https://music.bugs.co.kr/album/" + albumId;
        Document albumDataDoc = Jsoup.connect(albumDataUrl).get();
        Elements albumData = albumDataDoc.select("table[class=info] > tbody");

        String lyricsDataUrl = "https://music.bugs.co.kr/track/" + trackId;
        Document lyricsDataDoc = Jsoup.connect(lyricsDataUrl).get();

        MusicInfo musicInfo = MusicInfo.builder()
                .album_name(albumDataDoc.select("header[class=sectionPadding pgTitle noneLNB] > div[class=innerContainer] > h1").text())
                .artist_name(albumData.select("tr > td > a").first().text())
                .release_date(albumData.select("tr > td > time").text())
                .genre(albumData.select("tr > td > a").text().replace(albumData.select("tr > td > a").first().text(), ""))
                .lyrics_data(lyricsDataDoc.select("xmp").text())
                .build();

        return musicInfo;
    }

    public SearchInfo bringMusicHistoryList(Long userId){
        Optional<User> user = userRepository.findById(userId);

        return SearchInfo.builder().searchWords(user.get().getSearchWords().stream()
                .map(artistName -> artistName.getSearch_word())
                .collect(Collectors.toList())).build();
    }
}
