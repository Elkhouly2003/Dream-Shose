package com.example.dream_shops.dto;

import com.example.dream_shops.model.Image;
import com.example.dream_shops.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String brand ;
    private String description;
    private BigDecimal price;
    private int inventory;
    private String category ;
    private List<String> imagesDownloadUrls = new ArrayList<>();

    public static ProductDto productToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setInventory(product.getInventory());
        productDto.setCategory(product.getCategory().getName());
        productDto.setId(product.getId());
        productDto.setImagesDownloadUrls(showImagesDownLoadUrl(product.getImages()));

        return productDto;
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
