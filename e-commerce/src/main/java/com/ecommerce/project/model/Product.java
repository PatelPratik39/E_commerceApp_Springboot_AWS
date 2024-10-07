package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;
    @NotBlank
    @Size(min = 3, message = "Product name must contain atleast 3 characters")
    private String productName;

    @NotBlank
    @Size(min = 6, message = "Product description must contain atleast 6 characters")
    private String description;
    private String image;
    private double price;
    private double discount;
    private double specialPrice;
    private int quantity;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

//    to manager Seller-side scenarios
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

}
