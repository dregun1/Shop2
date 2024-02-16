package com.example.shop_17.item.service;

import com.example.shop_17.item.dto.ItemDto;
import com.example.shop_17.item.dto.ItemImgDto;
import com.example.shop_17.item.dto.ItemSearchDto;
import com.example.shop_17.item.dto.MainItemDto;
import com.example.shop_17.item.entity.Item;
import com.example.shop_17.item.entity.ItemImg;
import com.example.shop_17.item.repository.ItemImgRepository;
import com.example.shop_17.item.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemImgRepository itemImgRepository;
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;

    public Long saveItem(ItemDto itemDto, List<MultipartFile> itemImgFileList) throws IOException {

        Item item = itemDto.createItem();
        itemRepository.save(item);

        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i == 0){
                itemImg.setRepImgYn("Y");
            }else {
                itemImg.setRepImgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemDto getItemDetail(Long itemId){
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        ItemDto itemDto = ItemDto.of(item);
        itemDto.setItemImgDtoList(itemImgDtoList);

        return itemDto;
    }

    public Long updateItem(ItemDto itemDto, List<MultipartFile> itemImgFileList) throws IOException {

        Item item = itemRepository.findById(itemDto.getId()).orElseThrow(EntityNotFoundException::new);

        item.updateItem(itemDto);

        List<Long> itemImgIds = itemDto.getItemImgIds();

        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i),itemImgFileList.get(i));

        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.GetAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public ItemDto getItemDtl(Long itemId){
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemDto itemDto = ItemDto.of(item);
        itemDto.setItemImgDtoList(itemImgDtoList);
        return itemDto;
    }


}
