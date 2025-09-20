package com.example.dream_shops.service.product;

import com.example.dream_shops.model.Product;
import com.example.dream_shops.request.AddProductRequest;
import com.example.dream_shops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
  Product AddProduct(AddProductRequest product);
  Product getProductById(Long id);
  void deleteProductById(Long id);
  Product updateProduct(ProductUpdateRequest request , Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String categoryName);
    List<Product> getProductsByBrand(String brandName);
    List<Product> getProductsByCategoryAndBrand(String categoryName, String brandName);
    List<Product> getProductByNames(String productNames);
    List<Product> getProductsByBrandAndName(String brandName, String productNames);
    Long countProductsByBrandAndName(String categoryName , String brandName);

}
