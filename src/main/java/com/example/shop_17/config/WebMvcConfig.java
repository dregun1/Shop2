package com.example.shop_17.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")
    String uploadPath;

    //uploadPath가 무슨기능있는지 모르겠음. 이미지파일은  /shop이 아니라 /shop/item으로 들어옴. 그래서 주석 처리해봤지만 별다른 문제는 보이지 않음.
    //(!중요)위 코드는 "/images/**" 패턴의 URL 요청을 uploadPath에 지정된 디렉토리로 매핑합니다. 예를 들어, "/images/logo.png"와 같은 요청은 uploadPath에 지정된 디렉토리에서 실제 파일을 찾게 됩니다.
    // 이해했음 >> itemimg에서 img_url속성에 /images/item/e4128930-e6be-4ce4-a3bb-c2d128e039f7.jpg 이렇게 저장하기 때문에 /shop밑에 /item으로 시작되어서 찾아주는 거임.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }

}