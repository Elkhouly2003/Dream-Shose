package com.example.dream_shops.service.product;

import com.example.dream_shops.exceptions.CategoryNotFoundException;
import com.example.dream_shops.exceptions.ProductNotFoundException;
import com.example.dream_shops.model.Category;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.repository.CategoryRepository;
import com.example.dream_shops.repository.ProductRepository;
import com.example.dream_shops.request.AddProductRequest;
import com.example.dream_shops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository ;
    private final CategoryRepository categoryRepository ;

    @Override
    public Product AddProduct(AddProductRequest request) {

        Category category = categoryRepository.findCategoryByName(request.getCategory())
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory());
                    return categoryRepository.save(newCategory);
                });

          request.setCategory(request.getCategory());
       return productRepository.save(createProduct(request , category));
    }

    private Product createProduct(AddProductRequest request , Category category ) {
        return new Product(
                request.getName() ,
                request.getPrice() ,
                request.getDescription(),
                request.getBrand(),
                request.getInventory() ,
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product Not Found !"));
    }

    @Override
    public void deleteProductById(Long id) {
         productRepository.findById(id).ifPresentOrElse(productRepository::delete
                           , ()->{throw new ProductNotFoundException("Product Not Found !");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct , request))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("Product Not Found !" )) ;
    }
    private Product updateExistingProduct(Product existingProduct , ProductUpdateRequest request) {

        if(request.getName() != null) {
            existingProduct.setName(request.getName());
        }
        if(request.getPrice() != null) {
            existingProduct.setPrice(request.getPrice());
        }
        if(request.getDescription() != null) {
            existingProduct.setDescription(request.getDescription());
        }
        if(request.getBrand() != null) {
            existingProduct.setBrand(request.getBrand());
        }
        if(request.getInventory() != 0) {
            existingProduct.setInventory(request.getInventory());
        }
        if(request.getCategory() != null) {
            Category category = categoryRepository.findCategoryByName(request.getCategory().getName()).orElseThrow(() -> new CategoryNotFoundException("Category not found !"));
            existingProduct.setCategory(category);
        }

        return existingProduct ;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        return  productRepository.getProductsByCategoryName(categoryName);
    }

    @Override
    public List<Product> getProductsByBrand(String brandName) {
        return  productRepository.findByBrand(brandName) ;
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String categoryName, String brandName) {
        return productRepository.findByCategoryNameAndBrand(categoryName , brandName) ;
    }

    @Override
    public List<Product> getProductByNames(String productNames) {
        return productRepository.findByName(productNames) ;
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brandName, String productNames) {
        return productRepository.findByBrandAndName(brandName , productNames);
    }

    @Override
    public Long countProductsByBrandAndName(String categoryName , String brandName ) {
        return productRepository.countByBrandAndName(categoryName ,brandName);
    }


}
