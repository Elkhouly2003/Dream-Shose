package com.example.dream_shops.service.category;

import com.example.dream_shops.exceptions.AlreadyExistsException;
import com.example.dream_shops.exceptions.CategoryNotFoundException;
import com.example.dream_shops.model.Category;
import com.example.dream_shops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
       return categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category Not Found !")) ;

    }

    @Override
    public Category getCategoryByName(String name) {
        return  categoryRepository.findCategoryByName(name)
                .orElseThrow(()-> new CategoryNotFoundException("Category Not Found !")) ;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    @Override
    public Category addCategory(Category category) {
       return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(()->new AlreadyExistsException(category.getName()+" Already Exist"));

    }

    @Override
    public Category updateCategory(Category category ,Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());

            return categoryRepository.save(category);
        }).orElseThrow(()-> new CategoryNotFoundException("Category Not Found !"));


    }

    @Override
    public void deleteCategoryById(Long id) {
       categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete , ()->{
           throw new CategoryNotFoundException("Category Not Found !");
       });
    }
}
