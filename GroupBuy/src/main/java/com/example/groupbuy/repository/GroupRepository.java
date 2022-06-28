package com.example.groupbuy.repository;

import com.example.groupbuy.entity.GroupBuying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupBuying, Integer>, JpaSpecificationExecutor<GroupBuying> {
}
