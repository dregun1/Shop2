package com.example.shop_17.item.controller;

import com.example.shop_17.item.dto.ItemDto;
import com.example.shop_17.item.dto.ItemSearchDto;
import com.example.shop_17.item.entity.Item;
import com.example.shop_17.item.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto", new ItemDto());

        return "/item/itemForm";
    }

    //등록
    @PostMapping("/admin/item/new")
    public String itemNew(@Valid ItemDto itemDto, BindingResult bindingResult, Model model, @RequestParam("itemImgFile")List<MultipartFile>itemImgFileList){

        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입니다.");
            return "item/itemForm";
        }

        try {
            itemService.saveItem(itemDto, itemImgFileList);
        }catch(IOException e){
            model.addAttribute("errorMessage", "상품 등록 중에 오류 발생.");
            return "item/itemForm";
        }
        return "redirect:/";
    }

    //수정
    @GetMapping("/admin/item/{itemId}")
    public String itemDetail(@PathVariable("itemId")Long itemId, Model model){

        try {
            ItemDto itemFormDto = itemService.getItemDetail(itemId);
            model.addAttribute("itemFormDto", itemFormDto);

        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemDto());
            return "item/itemForm";
        }
        return "item/itemForm";
    }

    @PostMapping("/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemDto itemFormDto, BindingResult bindingResult, Model model, @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList) {

        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입니다.");
            return "item/itemForm";
        }
        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "상품 수정 중에 오류 발생.");
            return "item/itemForm";
        }
        return "redirect:/";
    }

    @GetMapping({"/admin/items", "/admin/items/{page}"})
    public String itemList(ItemSearchDto itemSearchDto, Model model, @PathVariable("page")Optional<Integer> page){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);


            return "item/itemList";
        }

    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){
        ItemDto itemDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemDto);
        return "item/itemDtl";
    }

}
