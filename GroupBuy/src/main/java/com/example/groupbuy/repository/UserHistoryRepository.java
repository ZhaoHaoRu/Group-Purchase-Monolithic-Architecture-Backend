package com.example.groupbuy.repository;

import com.example.groupbuy.entity.User;
import com.example.groupbuy.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Set;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer> {
    Set<UserHistory> findByUser(User user);

    UserHistory findByUserAndCategory(User user, String category);

    @Transactional
    @Modifying
    @Query(value = "update user_history set liking = liking + ?1 where user_id = ?2 and category = ?3", nativeQuery = true)
    void updateLiking(@Param("newLiking") Integer newLiking, @Param("userId") Integer userId, @Param("category") String category);
}
