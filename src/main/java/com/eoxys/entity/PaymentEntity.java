package com.eoxys.entity;	

import com.eoxys.utils.PaymentStatus;

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
@Table(name="tbl_payment")
public class PaymentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="payment_id")
	private Long paymentId;
	
	@Column(name="order_id")
	private Long orderId;
	
	private Long amount;
	private PaymentStatus paymentStatus;
	private String paymentMethod = "creditcard";
	
//	mapping between this payment page to order page to display all orders via order id in payment page
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, nullable = true, updatable = false)
    private OrdersEntity order_payment_info;

}
