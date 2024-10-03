package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Value("${project.images}")
    private String path;


    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, CategoryRepository categoryRepository, FileService fileService) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
    }

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        // Fetch the category from the repository first
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

//        condition check for product
        boolean isProductNotPresent = true;
        List<Product> products = category.getProducts();
        for(Product value : products){
            if(value.getProductName().equals(productDTO.getProductName())){
                isProductNotPresent = false;
                break;
            }
        }

       if(isProductNotPresent){
           Product product = modelMapper.map(productDTO, Product.class);
           product.setCategory(category);
           product.setImage("default.png");
           double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();
           product.setSpecialPrice(specialPrice);
           Product savedProduct = productRepository.save(product);
           return modelMapper.map(savedProduct,ProductDTO.class);
       } else {
           throw new APIException("Product already Exists!!");
       }
    }

    @Override
    public ProductResponse getAllProducts() {
//        condition check Product Size is zero or not



        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        if(products.isEmpty()){
            throw new APIException("No products found!");
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        //        condition check Product Size is zero or not

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);

        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        //        condition check Product Size is zero or not
        if(products.isEmpty()){
            throw new APIException("No products found!");
        }

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;

    }

    @Override
    public ProductResponse searchProductByCategory(String keyword) {
        //        condition check Product Size is zero or not

        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');

        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {

//        get the existing product from repo
        Product productFromDatabase = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        Product product = modelMapper.map(productDTO, Product.class);

//        update the product info with user who created requestBody
        productFromDatabase.setProductName(product.getProductName());
        productFromDatabase.setDescription(product.getDescription());
        productFromDatabase.setPrice(product.getPrice());
        productFromDatabase.setQuantity(product.getQuantity());
        productFromDatabase.setDiscount(product.getDiscount());
        productFromDatabase.setSpecialPrice(product.getSpecialPrice());

//        store updated product tp repo or database
        Product savedProduct = productRepository.save(productFromDatabase);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
//        get Product from database
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
//        upload image to server
//        Get the file name of upload image
//        String path = "images/";  //this is hard coded so i need to store in application.properties file
        String fileName = fileService.uploadImage(path, image);

//        updating the new file name to the product
        productFromDB.setImage(fileName);

//        save updatedProduct
        Product updatedProduct = productRepository.save(productFromDB);
//        return DTO after mapping product to DTO
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

}
