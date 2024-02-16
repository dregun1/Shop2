package com.example.shop_17.controller;

import com.example.shop_17.item.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping(value="/thymeleaf")
public class ThymeleafController {

    @GetMapping(value = "/ex1")
    public String ex1(Model model){
        model.addAttribute("data", "안녕 나야  잘 지내니");
        return "thymeleaf/ex1";
    }

    @GetMapping(value = "/ex2")
    public String ex2(Model model){

        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
 //       itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);

        return "thymeleaf/ex2";
    }

    @GetMapping(value = "/ex6")
    public void ex6(Model model){

    }
}
