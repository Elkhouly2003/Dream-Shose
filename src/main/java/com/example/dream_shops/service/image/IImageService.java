package com.example.dream_shops.service.image;

import com.example.dream_shops.dto.ImageDto;
import com.example.dream_shops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
     Image getImageById(Long id);
     void DeleteImageById(Long id);
     List<ImageDto> saveImages(List<MultipartFile> files , Long productId);
     void updateImage(MultipartFile image ,Long ImageId);

}
