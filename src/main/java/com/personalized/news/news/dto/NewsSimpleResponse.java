package com.personalized.news.news.dto;

import com.personalized.news.news.elasticsearch.NewsDocument;
import com.personalized.news.news.entity.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsSimpleResponse {

    private Long id;
    private String title;
    private String summarizedContent;
    private LocalDate publishedAt;
    private String imageUrl;
    private Long viewCount;


    public static NewsSimpleResponse from(News news) {
        return NewsSimpleResponse.builder()
                .id(news.getId())
                .title(news.getTitle())
                .summarizedContent(news.getSummarizedContent())
                .publishedAt(news.getPublishedAt())
                .imageUrl(news.getImageUrl())
                .viewCount(news.getViewCount())
                .build();
    }

    public static NewsSimpleResponse from(NewsDocument newsDocument) {
        return NewsSimpleResponse.builder()
                .id(newsDocument.getId())
                .title(newsDocument.getQuery())
                .summarizedContent(newsDocument.getSummarizedContent())
                .imageUrl(newsDocument.getImageUrl())
                .build();
    }

}
