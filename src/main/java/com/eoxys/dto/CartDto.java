package com.eoxys.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CartDto {
	@Column(name="user_id")
	 Long userId;
	private Long productId;
	private Long quantity = 1L;

}
