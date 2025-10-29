package com.personalized.news.news.service;

import com.personalized.news.news.dto.SearchHistoryResponse;
import com.personalized.news.news.entity.SearchHistory;
import com.personalized.news.news.entity.User;
import com.personalized.news.news.exception.UserNotFoundException;
import com.personalized.news.news.repository.SearchHistoryRepository;
import com.personalized.news.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchHistoryService {

    private static final int  MAX_SEARCH_HISTORY = 50;

    private final SearchHistoryRepository searchHistoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveSearchHistory(Long userId, String query){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        SearchHistory searchHistory = SearchHistory.from(user, query);
        searchHistoryRepository.save(searchHistory);
        List<SearchHistory> searchHistories = searchHistoryRepository.findByUserOrderByCreatedDateAsc(user);
        if(searchHistories.size() >= MAX_SEARCH_HISTORY){
            List<SearchHistory> deleteList = searchHistories.stream()
                    .limit(searchHistories.size() - MAX_SEARCH_HISTORY)
                    .collect(Collectors.toList());
            searchHistoryRepository.deleteAll(deleteList);
        }
    }

    @Transactional
    public void deleteSearchHistory(Long userId, String query){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        List<SearchHistory> searchHistory = searchHistoryRepository.findUserAndQuery(user);
        searchHistoryRepository.deleteAll(searchHistory);
    }

    public List<SearchHistoryResponse> getSearchHistory(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        PageRequest pageRequest = PageRequest.of(0,5);
        return searchHistoryRepository.findByUserOrderByCreatedDateDesc(user, pageRequest)
                .stream()
                .map(SearchHistoryResponse::from)
                .collect(Collectors.toList());
    }
}
