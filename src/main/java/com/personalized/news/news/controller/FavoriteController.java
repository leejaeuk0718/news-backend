package com.personalized.news.news.controller;

import com.personalized.news.news.dto.FavoriteSimpleResponse;
import com.personalized.news.news.entity.Favorite;
import com.personalized.news.news.security.CustomUserDetails;
import com.personalized.news.news.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    //뉴스 저장
    public ResponseEntity<Void> likeNews(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @PathVariable("news-id") Long newsId) {
        favoriteService.likeNews(newsId, userDetails.getId());

        return ResponseEntity.ok().build();
    }

    //뉴스 삭제
    public ResponseEntity<Void> UnLikeNews(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @PathVariable("news-id") Long newsId) {
        favoriteService.unlikeNews(userDetails.getId(), newsId);
        return ResponseEntity.ok().build();
    }

    //저장한 뉴스 목록 조회
    public ResponseEntity<List<FavoriteSimpleResponse>> getFavorites(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                                       @RequestParam(name = "size", defaultValue = "10") int size) {
        List<FavoriteSimpleResponse> userFavorites = favoriteService.getUserFavorites(userDetails.getId(), page, size);

        return ResponseEntity.ok(userFavorites);
    }

}
