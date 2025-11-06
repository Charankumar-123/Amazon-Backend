package com.eoxys.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_shipping")
public class ShippingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shipping_id")
	private Long shippingId;

	@Column(name = "order_id", nullable = false)
	private Long orderId;

	@Column(name = "tracking_number", unique = true)
	private String trackingNumber;

	@Column(name = "carrier")
	private String carrier = "fedx"; // e.g., FedEx, UPS

	@Column(name = "estimated_delivery_date")
	@Temporal(TemporalType.DATE)
	private Date estimatedDeliveryDate;

	@Column(name = "status")
	private String status; // "Processing", "Shipped", "Delivered"

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, nullable = true, updatable = false)
	private OrdersEntity orderShipping;

	
}
