package com.example.shop_17.item.service;

import com.example.shop_17.item.entity.ItemImg;
import com.example.shop_17.item.repository.ItemImgRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgLocation,oriImgName,itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long ItemImgId, MultipartFile itemImgFile) throws IOException {
        if(!itemImgFile.isEmpty()){
            ItemImg itemImg = itemImgRepository.findById(ItemImgId).orElseThrow(EntityNotFoundException::new);
            if(!StringUtils.isEmpty(itemImg.getImgName())){
                fileService.deleteFile(itemImgLocation + "/" + itemImg.getImgName());
            }

            String oriName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriName, itemImgFile.getBytes()); //여기서 물리위치에 파일등록하는듯.
            String imgUrl = "/images/item/" + imgName;

            itemImg.updateItemImg(oriName, imgName, imgUrl);

        }

    }



}
