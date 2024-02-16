package com.example.shop_17.item.entity;

import com.example.shop_17.item.constant.ItemSellStatus;
import com.example.shop_17.item.dto.ItemDto;
import com.example.shop_17.utils.entity.BaseEntity;
import com.example.shop_17.utils.exception.OutOfStockException;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "item_id")
    private Long id; //상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm; //상품이름

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, name = "number")
    private int stockNumber; //재고 수량

    @Lob   //긴 글~
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품 판매 상태

    public void updateItem(ItemDto itemDto){
        this.itemNm = itemDto.getItemNm();
        this.price = itemDto.getPrice();
        this.stockNumber = itemDto.getStockNumber();
        this.itemDetail = itemDto.getItemDetail();
        this.itemSellStatus = itemDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if(restStock<0){
            throw new OutOfStockException("상품의 재고가 부족합니다.(현재 재고 수량: " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;

    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

/*    @CreationTimestamp
    private LocalDateTime regTime; //등록시간

    @UpdateTimestamp
    private LocalDateTime updateTime; //수정시간*/

}
