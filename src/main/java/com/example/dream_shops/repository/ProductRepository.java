package com.example.dream_shops.repository;

import com.example.dream_shops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> getProductsByCategoryName(String category);

    List<Product> findByBrand(String brandName);

    List<Product> findByCategoryNameAndBrand(String categoryName, String brandName);

    List<Product> findByName(String productNames );

    List<Product> findByBrandAndName(String brandName, String productNames);

    Long countByBrandAndName(String brandName, String productNames);
}
