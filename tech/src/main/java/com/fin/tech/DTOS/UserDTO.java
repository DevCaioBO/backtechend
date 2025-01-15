package com.fin.tech.DTOS;

import com.fin.tech.ENUMS.UserRoles;

public class UserDTO {
	
	private Integer id;
	
	private String login;
	
	private String password;
	
	private String email;
	
	private UserRoles role;
	
	private String image_perfil;
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage_perfil() {
		return image_perfil;
	}

	public void setImage_perfil(String image_perfil) {
		this.image_perfil = image_perfil;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRoles getRole() {
		return role;
	}

	public void setRole(UserRoles role) {
		this.role = role;
	}
	
	
}
