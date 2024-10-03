package com.ecommerce.project.service;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;

public interface ProductService {

    public ProductDTO addProduct(Long categoryId, Product product);
}
