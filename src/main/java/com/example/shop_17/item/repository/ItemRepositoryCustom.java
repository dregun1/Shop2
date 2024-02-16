package com.example.shop_17.item.repository;

import com.example.shop_17.item.dto.ItemSearchDto;
import com.example.shop_17.item.dto.MainItemDto;
import com.example.shop_17.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> GetAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
