package com.personalized.news.news.elasticsearch;


import com.personalized.news.news.dto.NewsSimpleResponse;
import com.personalized.news.news.entity.News;
import com.personalized.news.news.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ElasticNewsService {

    private final NewsDocumentRepository newsDocumentRepository;
    private final SearchHistoryService searchHistoryService;

    @Transactional
    public void saveNews(News news) {
        NewsDocument newsDocument = NewsDocument.from(news);
        newsDocumentRepository.save(newsDocument);
    }

    @Cacheable(value = "newsSearch", key = "#query")
    @Transactional
    public List<NewsSimpleResponse> searchNewsByQuery(Long userId, String query) {
        searchHistoryService.saveSearchHistory(userId, query);
        List<NewsDocument> newsDocuments = newsDocumentRepository.findByQueryContainingIgnoreCase(query);

        return newsDocuments.stream()
                .map(NewsSimpleResponse::from)
                .collect(Collectors.toList());
    }
}
