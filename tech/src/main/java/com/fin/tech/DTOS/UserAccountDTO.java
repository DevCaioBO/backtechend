package com.fin.tech.DTOS;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fin.tech.ENUMS.UserRoles;

public class UserAccountDTO {
	
	private Integer id_user;
	private Integer id_accounts;
	private String login;
	private UserRoles role;
	private LocalDate created_at;
	private LocalDate end_date;
	private String name_accounts;
	private BigDecimal saved_amount;
	private LocalDate start_date;
	private BigDecimal target_amount;
	public Integer getId_user() {
		return id_user;
	}
	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}
	public Integer getid_accounts() {
		return id_accounts;
	}
	public void setid_accounts(Integer id_accounts) {
		this.id_accounts = id_accounts;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public UserRoles getRole() {
		return role;
	}
	public void setRole(UserRoles role) {
		this.role = role;
	}
	public LocalDate getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}
	public LocalDate getEnd_date() {
		return end_date;
	}
	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}
	public String getname_accounts() {
		return name_accounts;
	}
	public void setname_accounts(String name_accounts) {
		this.name_accounts = name_accounts;
	}
	public BigDecimal getSaved_amount() {
		return saved_amount;
	}
	public void setSaved_amount(BigDecimal saved_amount) {
		this.saved_amount = saved_amount;
	}
	public LocalDate getStart_date() {
		return start_date;
	}
	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}
	public BigDecimal getTarget_amount() {
		return target_amount;
	}
	public void setTarget_amount(BigDecimal target_amount) {
		this.target_amount = target_amount;
	}
	
	
		
}
