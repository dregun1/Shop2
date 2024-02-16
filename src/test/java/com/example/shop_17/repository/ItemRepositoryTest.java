package com.example.shop_17.repository;


import com.example.shop_17.item.constant.ItemSellStatus;
import com.example.shop_17.item.entity.Item;
import com.example.shop_17.item.entity.QItem;
import com.example.shop_17.item.repository.ItemRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager em;


    public void createItemList(){
        for(int i=1;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100); item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("JPQL 쿼리")
    public void findByItemDetailTest(){
        this.createItemList();

        List<Item> itemList = itemRepository.findByItemDetail("테스트");
        for(Item item : itemList){
            System.out.println(item);
        }

    }

    @Test
    @DisplayName("Native 쿼리")
    public void findByItemDetailNativeTest(){
        this.createItemList();

        List<Item> itemList = itemRepository.findByItemDetailNative("테스트");
        for(Item item : itemList){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("querydsl 테스트")
    public void querydslTest(){
        createItemList();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        // QItem qItem = new QItem("i");
        QItem qItem = QItem.item;

/*        JPAQuery<Item> query =
                jpaQueryFactory.select(qItem).from(qItem).where(qItem.itemSellStatus.eq(ItemSellStatus.SELL)).orderBy(qItem.price.desc());

        List<Item> list = query.fetch();*/

        List<Item> list =
        jpaQueryFactory.select(qItem).from(qItem).where(qItem.itemSellStatus.eq(ItemSellStatus.SELL)).orderBy(qItem.price.desc()).fetch();


        for(Item item : list){
            System.out.println(item);
        }
    }

    public void createItemList2(){
        for(int i=1;i<=5;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }

        for(int i=6;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }



    @Test
    @DisplayName("querydsl 테스트2")
    public void querydslTest2(){
        createItemList2();
        String itemDetail = "테스트";
        int price = 10003;
        String itemSellState = "SELL";

        QItem item = QItem.item;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(item.itemDetail.like("%" + itemDetail + "%"));
        builder.and(item.price.gt(price));

        if(StringUtils.equals(itemSellState, ItemSellStatus.SELL)){
            builder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5); //보여줄 페이지, 양

        Page<Item> findAll = itemRepository.findAll(builder, pageable);

        System.out.println("전체 갯수 : " + findAll.getTotalElements());

        List<Item> content = findAll.getContent();
        for (Item item2: content) {
            System.out.println(item2);
            
        }

    }
}