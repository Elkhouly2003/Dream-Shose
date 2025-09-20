package com.example.dream_shops.dto.response;

import com.example.dream_shops.dto.ProductDto;
import com.example.dream_shops.model.Image;
import com.example.dream_shops.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductInfoDto {
    private Long id;
    private String name;
    private String brand ;
    private String description;

    private List<String> imagesDownloadUrls = new ArrayList<>();


    public static ProductInfoDto productToProductInfoDto(Product product) {

        ProductInfoDto productInfoDto = new ProductInfoDto();
        productInfoDto.setId(product.getId());
        productInfoDto.setName(product.getName());
        productInfoDto.setBrand(product.getBrand());
        productInfoDto.setDescription(product.getDescription());

        productInfoDto.setImagesDownloadUrls(showImagesDownLoadUrl(product.getImages()));

        return productInfoDto;
    }

    private static List<String> showImagesDownLoadUrl(List<Image> images) {
        List<String> urls = new ArrayList<>();

        if(images != null) {
            for (Image image : images) {
                urls.add(image.getDawnLoadUrl());
            }
        }
        else
            urls.add(null);

        return urls;
    }
}
