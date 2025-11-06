package com.eoxys.entity;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;



@Entity
@Data
@Table(name="tbl_users")
public class UsersEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;

	@Column(name="User_Name")
	private String userName;
	
	@Column(name="Email")
	private String email;
	
	@Column(name="Mobile")
	private Long mobile;
	
	@Column(name="Password")
	private String password;
	
	@Column(name="Role")
	private String role;
	
	@OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<CartEntity> carts = new ArrayList<>();
	
	
}
