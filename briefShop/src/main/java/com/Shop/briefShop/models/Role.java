package com.Shop.briefShop.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

//	@OneToMany(mappedBy = "roleId", cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "roleId", cascade = CascadeType.ALL)
	private Set<User> users = new HashSet<>();

}