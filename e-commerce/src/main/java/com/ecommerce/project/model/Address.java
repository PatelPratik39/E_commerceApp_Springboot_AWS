package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "Street name must be atleast 5 characters")
    private String street;

    @Size(min = 5, message = "Building name must be atleast 2 characters")
    private String BuildingName;
    @NotBlank
    @Size(min = 3, message = "City name must be atleast 3 characters")
    private String city;
    @NotBlank
    @Size(min = 2, message = "State name must be atleast 2 characters")
    private String state;
    @NotBlank
    @Size(min = 5, message = "zipcode name must be atleast 5 characters")
    private String zip;

    @NotBlank
    @Size(min = 3, message = "Country name must be atleast 3 characters")
    private String country;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(Long addressId, String street, String buildingName, String city, String state, String zip, String country ) {
        this.addressId = addressId;
        this.street = street;
        BuildingName = buildingName;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }
}
