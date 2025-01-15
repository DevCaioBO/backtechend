package com.fin.tech.DTOS;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fin.tech.models.UsersModel;

public class AccountTransactionDTO {
	
	private Integer id_goals;
	private Integer id_transact;
	
	
	
	public AccountTransactionDTO(Integer id_goals, Integer id_transact) {
		super();
		this.id_goals = id_goals;
		this.id_transact = id_transact;
	}
	
	
	
	
	public AccountTransactionDTO() {
		super();
	}




	public Integer getId_goals() {
		return id_goals;
	}
	public void setId_goals(Integer id_goals) {
		this.id_goals = id_goals;
	}
	public Integer getId_transact() {
		return id_transact;
	}
	public void setId_transact(Integer id_transact) {
		this.id_transact = id_transact;
	}
	
	
	
	
}
