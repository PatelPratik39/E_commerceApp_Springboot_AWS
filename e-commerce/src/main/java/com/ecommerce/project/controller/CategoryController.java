package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/public")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/echo")
    public ResponseEntity<String> echoMessage(@RequestParam(name = "message", required = false) String message) {
        return new ResponseEntity<>("Echoed message: " + message, HttpStatus.OK);
    }

    @GetMapping("/categories")
    //@RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }


    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategortDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategortDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid
                                                 @RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable Long categoryId) {
            CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>( updatedCategoryDTO, HttpStatus.OK);
    }

}
