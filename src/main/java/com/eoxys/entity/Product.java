package com.eoxys.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_product")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	@Column(name="product_id")
	private Long productId;
    private String name;
    private String description;
    private String image;
    private String category;
    private Integer quantity;
    private float price;
   
}
