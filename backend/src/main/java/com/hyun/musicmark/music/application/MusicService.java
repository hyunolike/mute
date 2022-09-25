package com.hyun.musicmark.music.application;

import com.hyun.musicmark.music.ui.dto.MusicInfo;
import com.hyun.musicmark.music.ui.dto.SearchMusicInfo;
import com.hyun.musicmark.music.ui.dto.SearchMusicInfoResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. vibe
 * 가수 검색: https://apis.naver.com/vibeWeb/musicapiweb/v3/search/artist?query=
 * 트랙 검색: https://apis.naver.com/vibeWeb/musicapiweb/v3/search/track?query=
 * 앨범 검색: https://apis.naver.com/vibeWeb/musicapiweb/v3/search/album?query=
 *
 * 2. bugs
 * 트랙 검색(가사 정보 포함): https://music.bugs.co.kr/track/[트랙id]
 * 앨범 검색: https://music.bugs.co.kr/album/[앨범id]
 */

@Service
public class MusicService {

    public SearchMusicInfoResponse searchMusicInfo(String articleName) throws IOException {
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


}
