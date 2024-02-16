package com.example.shop_17.controller;

import com.example.shop_17.item.dto.ItemSearchDto;
import com.example.shop_17.item.dto.MainItemDto;
import com.example.shop_17.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping(value = "/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);  //0은 첫 번째 페이지
        //Optional.isPresent() 메소드는 Optional 객체에 값이 있는지 여부를 확인하는 데 사용됩니다.
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable); //return new PageImpl<>(content, pageable, total);
        model.addAttribute("items", items); //페이지
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "main";

    }
}
