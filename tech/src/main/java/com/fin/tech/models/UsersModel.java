package com.fin.tech.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fin.tech.ENUMS.UserRoles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name="users")
@Table(name="users")

public class UsersModel implements  UserDetails {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_user")
	private Integer id_user;
	
	@Column(name="login")
	private String login;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	private String email;
	
	@Column(name="googleid")
	private String googleId;
	
	

	public String getGoogleId() {
		return googleId;
	}



	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	@Column(name="role")
	@Enumerated(EnumType.STRING)
	private UserRoles role;
	
	@Column(name="image_perfil")
	private String image_perfil;
	
	@OneToMany(mappedBy="id_user",cascade = CascadeType.ALL)
	private List<AccountsModel> accounts;
	
	//@OneToMany(mappedBy="id_user",cascade = CascadeType.ALL)
	//private List<TransactionsModel> transaction;
	
	
	
	
	

	public UsersModel(Integer id_user, String login, String password, UserRoles role) {
		super();
		this.id_user = id_user;
		this.login = login;
		this.password = password;
		this.role = role;
	}
	
	

	public UsersModel() {
		super();
	}



	public Integer getId() {
		return id_user;
	}

	public void setId(Integer id_user) {
		this.id_user = id_user;
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
	
	
	
	

	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
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



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == UserRoles.ADMIN) 
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
				new SimpleGrantedAuthority("ROLE_USER"));
		else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}


	@Override 
	public String getUsername() {

		return login;
	}
	
	@Override
	public boolean isAccountNonExpired() {
	
	    return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	  
	    return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	 
	    return true;
	}

	@Override
	public boolean isEnabled() {
	   
	    return true;
	}
	

	
	
}
