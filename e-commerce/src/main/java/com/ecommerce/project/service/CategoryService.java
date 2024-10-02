package com.ecommerce.project.service;


import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer sizeNumber, String sortBy, String sortOrder);
//    List<CategoryResponse> getAllCategories();

//    void createCategory(Category category);
    CategoryDTO createCategory(CategoryDTO categoryDTO);

//    void createCategories(List<Category> categories);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
