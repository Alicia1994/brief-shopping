package com.Shop.briefShop.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(	name = "users", uniqueConstraints = {
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	private String username;
	private String email;
	private String presentation;
	private String password;
	private Long roleId;

	public User(String username, String email, String presentation, String password) {
		this.username = username;
		this.email = email;
		this.presentation = presentation;
		this.password = password;
	}

	public User(String username, String email, String presentation, Long role, String encode) {
	this.username = username;
		this.email = email;
		this.presentation = presentation;
		this.roleId = role;
		this.password = encode;

	}
}
