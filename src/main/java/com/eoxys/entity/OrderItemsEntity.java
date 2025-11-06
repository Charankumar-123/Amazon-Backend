package com.eoxys.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="tbl_order_items")
public class OrderItemsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_item_id")
	private Long orderItemId;
	
	@Column(name="order_id")
	private Long orderId;
	
	@Column(name="product_id")
	private Long productId;
	
	@Column(name="quantity")
	private Long quantity;
	
	@Column(name="price")
	private float price;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name="product_id",referencedColumnName ="product_id",insertable = false, nullable = true, updatable = false)
	private Product productOrderInfo;

}
