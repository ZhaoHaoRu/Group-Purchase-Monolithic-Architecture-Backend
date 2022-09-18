package com.example.groupbuy.repository;

import com.example.groupbuy.entity.GroupBuying;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;
@Repository
public interface GroupRepository extends JpaRepository<GroupBuying, Integer>, JpaSpecificationExecutor<GroupBuying> {
    Set<GroupBuying> findAllByCategoryAndState(String category, Integer state);

    @Query(value = "from GroupBuying where category = :category and (state = 1 or state = 2)")
    Set<GroupBuying> selectGroupsByTag(String category);

    @Query(value = "from GroupBuying where state = 1 or state = 2")
    Set<GroupBuying> selectAllByStateIsNot();

    GroupBuying findByGroupId(int id);

    @Transactional
    @Modifying
    @Query(value = "update groupbuying set state = 0 where group_id = :groupId",nativeQuery = true)
    void updateGroupState(@Param("groupId")Integer groupId);

    @Transactional
    @Modifying
    @Query(value = "update groupbuying set category = :category, duration = :duration," +
            "group_info = :groupInfo, group_title = :groupTitle, start_time = :startTime where group_id = :groupId",nativeQuery = true)
    void updateGroup(@Param("category")String category, @Param("duration")Integer duration,
                     @Param("groupInfo")String groupInfo, @Param("group_title")String groupTitle,
                     @Param("start_time")Timestamp startTime, @Param("group_id")Integer groupId);

    @Query(value = "from GroupBuying where state = 2")
    Set<GroupBuying> queryAllSecKillGoods();

    @Transactional
    @Modifying
    @Query(value = "update groupbuying set popularity = popularity + :newPopularity where group_id = :groupId", nativeQuery = true)
    void updatePopularity(@Param("newPopularity") Integer newPopularity, @Param("groupId") Integer groupId);
}
