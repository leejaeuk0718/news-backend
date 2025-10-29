package com.personalized.news.news.exception;

public class FavoriteNotFoundException extends RuntimeException{

    public FavoriteNotFoundException(Long userId, Long newsId) {
        super("Favorite not found for user ID: " + userId + " and news ID: " + newsId);
    }
}
