package com.ecommerce.project.repository;

import com.ecommerce.project.model.Category;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAll();

    Category findByCategoryName(String categoryName);
}
