package com.example.shop_17.item.repository;

import com.example.shop_17.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    List<Item> findByItemNm(String ItemNm);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    //JPQL 쿼리
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price asc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    //Native 쿼리 ITEM(클래스명) >>> item(테이블명)
    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price asc", nativeQuery = true)
    List<Item> findByItemDetailNative(@Param("itemDetail") String itemDetail);

}
