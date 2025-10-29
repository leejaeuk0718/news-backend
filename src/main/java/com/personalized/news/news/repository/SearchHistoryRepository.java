package com.personalized.news.news.repository;

import com.personalized.news.news.entity.SearchHistory;
import com.personalized.news.news.entity.User;
import io.lettuce.core.ScanIterator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {
    List<SearchHistory> findByUserOrderByCreatedDateAsc(User user);

    List<SearchHistory> findUserAndQuery(User user);

    Page<SearchHistory> findByUserOrderByCreatedDateDesc(User user, PageRequest pageRequest);
}
