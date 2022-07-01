package com.example.groupbuy.repository;

import com.example.groupbuy.entity.GroupBuying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface GroupRepository extends JpaRepository<GroupBuying, Integer>, JpaSpecificationExecutor<GroupBuying> {
    Set<GroupBuying> findAllByCategoryAndState(String category, Integer state);

    @Query(value = "from GroupBuying where category = :category and (state = 1 or state = 2)")
    Set<GroupBuying> selectGroupsByTag(String category);

    @Query(value = "from GroupBuying where state = 1 or state = 2")
    Set<GroupBuying> selectAllByStateIsNot();

    GroupBuying findByGroupId(int id);
}
