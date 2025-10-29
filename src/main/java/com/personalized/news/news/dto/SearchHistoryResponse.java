package com.personalized.news.news.dto;


import com.personalized.news.news.entity.SearchHistory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHistoryResponse {

    private String query;

    public static SearchHistoryResponse from(SearchHistory searchHistory) {
        return SearchHistoryResponse
                .builder()
                .query(searchHistory.getQuery())
                .build();
    }
}
