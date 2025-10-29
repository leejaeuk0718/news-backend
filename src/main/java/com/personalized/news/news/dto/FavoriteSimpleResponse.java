package com.personalized.news.news.dto;

import com.personalized.news.news.entity.Favorite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteSimpleResponse {

    private Long newsId;

    private String title;

    private String summarizedContent;

    private String imgUrl;

    private LocalDate publishedAt;

    public static FavoriteSimpleResponse from(Favorite favorite) {
        return FavoriteSimpleResponse.builder()
                .newsId(favorite.getNews().getId())
                .title(favorite.getNews().getTitle())
                .summarizedContent(favorite.getNews().getSummarizedContent())
                .imgUrl(favorite.getNews().getImageUrl())
                .publishedAt(favorite.getNews().getPublishedAt())
                .build();
    }
}
