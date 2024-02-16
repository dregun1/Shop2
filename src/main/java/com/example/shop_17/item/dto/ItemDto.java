package com.example.shop_17.item.dto;

import com.example.shop_17.item.constant.ItemSellStatus;
import com.example.shop_17.item.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemDto {
    private Long id; //상품 코드

    @NotBlank(message = "상품명은 필수 항목입니다.")
    private String itemNm; //상품이름

    @NotNull(message = "가격은 필수 항목입니다.")
    private int price;

    @NotNull(message = "재고는 필수 항목입니다.")
    private int stockNumber; //재고 수량

    @NotBlank(message = "상품 설명은 필수 항목입니다.")
    private String itemDetail; //상품 상세 설명

    private ItemSellStatus itemSellStatus; //상품 판매 상태

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    public static ItemDto of(Item item){
        return modelMapper.map(item, ItemDto.class);
    }
}
