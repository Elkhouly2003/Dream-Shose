package com.example.dream_shops.controller;
import com.example.dream_shops.dto.ProductDto;
import com.example.dream_shops.model.Category;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.request.AddProductRequest;
import com.example.dream_shops.request.ProductUpdateRequest;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")

public class ProductController {
    private final ProductService productService ;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDto> productsRes = new ArrayList<>();

            for(Product product : products) {
                ProductDto productDto = ProductDto.productToDto(product);
                productsRes.add(productDto);
            }
            return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success !",productsRes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error !",e.getMessage()));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto res = ProductDto.productToDto(product);

            return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Found success ! ",res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("query success !",e.getMessage()));
        }

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody AddProductRequest product) {
        try {
            Product newProduct = productService.AddProduct(product);
            ProductDto res = ProductDto.productToDto(newProduct);
            return ResponseEntity.ok().body(new ApiResponse("Add product success ! ",res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error !",e.getMessage()));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest product) {
        try {
            Product oldProduct = productService.getProductById(id);
            oldProduct = productService.updateProduct(product , id);
            ProductDto res = ProductDto.productToDto(oldProduct);
            return ResponseEntity.ok().body(new ApiResponse("Update product success ! ",res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error !",e.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {

        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok().body(new ApiResponse("Delete product success ! ","Deleted id : "+id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error !",e.getMessage()));
        }
    }

    @GetMapping("/By-brandAnd-name/{brandName}/{productName}")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@PathVariable String brandName, @PathVariable String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName , productName) ;

            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("No Product Found ", null));
            }
            List<ProductDto> productsRes = new ArrayList<>();

            for(Product product : products) {
                ProductDto productDto = ProductDto.productToDto(product);
                productsRes.add(productDto);
            }
            return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success !",productsRes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage() , null));

        }

    }
    @GetMapping("/by-category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByBrandAndCategory(@RequestParam String brandName, @RequestParam String categoryName) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName , brandName);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("No Product Found ", null));
            }
            List<ProductDto> productsRes = new ArrayList<>();

            for(Product product : products) {
                ProductDto productDto = ProductDto.productToDto(product);
                productsRes.add(productDto);
            }
            return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success !",productsRes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage() , null));
        }

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductByNames(name) ;
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("No Product Found ", null));
            }
            List<ProductDto> productsRes = new ArrayList<>();

            for(Product product : products) {
                ProductDto productDto = ProductDto.productToDto(product);
                productsRes.add(productDto);
            }

            return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success !",productsRes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage() , e.getMessage()));
        }
    }

    @GetMapping("/by-brand/{BrandName}")
    public ResponseEntity<ApiResponse> getProductsByBrandName(@PathVariable String BrandName) {
        try {
            List<Product> products = productService.getProductsByBrand(BrandName) ;
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("No Product Found ", null));
            }
            List<ProductDto> productsRes = new ArrayList<>();

            for(Product product : products) {
                ProductDto productDto = ProductDto.productToDto(product);
                productsRes.add(productDto);
            }
            return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success !",productsRes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage() , e.getMessage()));
        }
    }

    @GetMapping("/by-category/{categoryName}")
    public ResponseEntity<ApiResponse> getProductsByCategoryName(@PathVariable String categoryName) {
        try {
            List<Product> products = productService.getProductsByCategory(categoryName) ;
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("No Product Found ", null));
            }

            List<ProductDto> productsRes = new ArrayList<>();

            for(Product product : products) {
                ProductDto productDto = ProductDto.productToDto(product);
                productsRes.add(productDto);
            }

            return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success !",productsRes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage() , e.getMessage()));
        }
    }

    @GetMapping("/countBy-brand-and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            var productCount = productService.countProductsByBrandAndName(brandName , productName);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product Count ! ","Count : "+productCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage() , null));
        }

    }


}
