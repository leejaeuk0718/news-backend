package com.personalized.news.news.controller;


import com.personalized.news.news.dto.NewsResponse;
import com.personalized.news.news.dto.NewsSimpleResponse;
import com.personalized.news.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    //전체 뉴스 조회
    @GetMapping
    public ResponseEntity<List<NewsSimpleResponse>> getNews(@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "15") int size) {
        List<NewsSimpleResponse> newsSimpleResponses = newsService.getAllNews(page, size);

        return  ResponseEntity.ok(newsSimpleResponses);
    }

    //특정뉴스상세조회
    @GetMapping("/{news_id}")
    public ResponseEntity<NewsResponse> getNewsById(@PathVariable(name = "news_id") Long newsId) {
        NewsResponse newsResponse = newsService.getNews(newsId);

        return  ResponseEntity.ok(newsResponse);
    }
}