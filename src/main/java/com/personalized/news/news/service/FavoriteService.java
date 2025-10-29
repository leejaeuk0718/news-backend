package com.personalized.news.news.service;

import com.personalized.news.news.dto.FavoriteSimpleResponse;
import com.personalized.news.news.entity.Favorite;
import com.personalized.news.news.entity.News;
import com.personalized.news.news.entity.User;
import com.personalized.news.news.exception.FavoriteNotFoundException;
import com.personalized.news.news.exception.NewsNotFoundException;
import com.personalized.news.news.exception.UserNotFoundException;
import com.personalized.news.news.repository.FavoriteRepository;
import com.personalized.news.news.repository.NewsRepository;
import com.personalized.news.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {

    private final NewsRepository newsRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likeNews(Long newsId,Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(newsId));

        Favorite favorite = Favorite.from(user, news);

        favoriteRepository.save(favorite);

        log.info("User {} liked news {}", userId, newsId);
    }

    @Transactional
    public void unlikeNews(Long newsId,Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(newsId));

        Favorite favorite = favoriteRepository.findByUserAndNews(user,news)
                .orElseThrow(() -> new FavoriteNotFoundException(userId, newsId));

        favoriteRepository.delete(favorite);

        log.info("User {} unliked news {}", userId, newsId);
    }

    public List<FavoriteSimpleResponse> getUserFavorites(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Favorite> favoritePage = favoriteRepository.findAllByUserWithNews(user, pageRequest);

        return favoritePage.stream()
                .map(FavoriteSimpleResponse::from)
                .collect(Collectors.toList());
    }
}
