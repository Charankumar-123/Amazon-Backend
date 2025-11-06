package com.eoxys.entity;

import java.sql.Date;

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
import lombok.Data;

@Entity
@Data
@Table(name="tbl_orderTracking")
public class OrderTrackingEntity {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tracking_id")
	private Long trackingId;
	
	@Column(name="order_id")
	private Long orderId;
	
	private String trackingNumber;
	private String Carrier;
	private Date estimatedDeliveryDate;
	private String status;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, nullable = true, updatable = false)
    private OrdersEntity orderTrackingInfo;

	
}
