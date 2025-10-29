package com.personalized.news.news.controller;

import com.personalized.news.news.dto.NewsResponse;
import com.personalized.news.news.dto.NewsSimpleResponse;
import com.personalized.news.news.dto.SearchHistoryResponse;
import com.personalized.news.news.elasticsearch.ElasticNewsService;
import com.personalized.news.news.entity.SearchHistory;
import com.personalized.news.news.security.CustomUserDetails;
import com.personalized.news.news.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/searchHistory")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;
    private final ElasticNewsService elasticNewsService;

    //뉴스 검색및 검색 기록 저장
    public ResponseEntity<List<NewsSimpleResponse>> searchNewsByHistory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                        @RequestParam("query") String query){
        List<NewsSimpleResponse> newsSimpleResponses = elasticNewsService.searchNewsByQuery(userDetails.getId(), query);

        return ResponseEntity.ok(newsSimpleResponses);
    }
    //뉴스 검색 기록 삭제
     public ResponseEntity<Void> deleteSearchHistory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                             @RequestParam("query") String query){
        searchHistoryService.deleteSearchHistory(userDetails.getId(), query);
        return ResponseEntity.ok().build();
     }
    //검색 기록 최근 5개 조회
    public ResponseEntity<List<SearchHistoryResponse>> etRecentSearchQueries(@AuthenticationPrincipal CustomUserDetails userDetails){
        List<SearchHistoryResponse> recentQueries = searchHistoryService.getSearchHistory(userDetails.getId());
        return ResponseEntity.ok(recentQueries);
    }
}
