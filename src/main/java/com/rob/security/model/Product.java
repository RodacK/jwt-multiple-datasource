package com.rob.security.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
public class Product {

    @Id
    private String name;
    private String description;

}
