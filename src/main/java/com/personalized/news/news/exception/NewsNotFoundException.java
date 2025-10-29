package com.personalized.news.news.exception;

public class NewsNotFoundException extends RuntimeException {
    public NewsNotFoundException(Long newsId) {
        super(newsId + "의 뉴스를 찾을수 없습니다.");
    }
}
