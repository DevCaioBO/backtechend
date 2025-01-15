package com.fin.tech.DTOS;

import org.springframework.web.multipart.MultipartFile;

import com.fin.tech.ENUMS.UserRoles;

public class RegisterDTO {
	private String login;
	private String password;
	private String email;
	private UserRoles role;
	
	private MultipartFile image_perfil;
	
	
	
	public MultipartFile getImage_perfil() {
		return image_perfil;
	}
	public void setImage_perfil(MultipartFile image_perfil) {
		this.image_perfil = image_perfil;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserRoles getRole() {
		return role;
	}
	public void setRole(UserRoles role) {
		this.role = role;
	}
	
	
	

}
