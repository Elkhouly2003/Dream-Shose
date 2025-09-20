package com.example.dream_shops.controller;

import com.example.dream_shops.dto.ImageDto;
import com.example.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dream_shops.model.Image;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.sql.SQLException;
import java.util.List;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> savedImages(@RequestParam List<MultipartFile> files ,@RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return  ResponseEntity.ok(new ApiResponse("Upload Success", imageDtos)) ;
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed", e.getMessage()));
        }
    }


    @GetMapping("/{imageId}")
    public ResponseEntity<?> downloadImage(@PathVariable Long imageId) throws SQLException {
        try {
            Image image = imageService.getImageById(imageId);

            ByteArrayResource  resource= new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()))  ;

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION , "attachment; filename = \" "+image.getFileName() +"")
                    .body(resource);
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Query success", e.getMessage()));
        }

    }

    @PutMapping("/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestParam MultipartFile file) {
        try {
            Image image  = imageService.getImageById(imageId);
            if(image != null) {
                imageService.updateImage(file,imageId);
                return  ResponseEntity.ok(new ApiResponse("Update Success", imageId)) ;
            }
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Update failed", e.getMessage()));
        }

        return ResponseEntity.status(OK).body(new ApiResponse("Updated failed" , INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image  = imageService.getImageById(imageId);
            if(image != null) {
                imageService.DeleteImageById(imageId);
                return  ResponseEntity.ok(new ApiResponse("delete Success !", imageId)) ;
            }
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Delete failed 1", e.getMessage()));
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed" , " server error !"));
    }


}
