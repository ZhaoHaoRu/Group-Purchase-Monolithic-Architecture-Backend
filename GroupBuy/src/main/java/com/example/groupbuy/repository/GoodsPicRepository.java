package com.example.groupbuy.repository;

import com.example.groupbuy.entity.GoodsPic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsPicRepository extends MongoRepository<GoodsPic, Integer> {

    GoodsPic findByGoodsId(Integer goodsId);

    GoodsPic save(GoodsPic goodsPic);
}
