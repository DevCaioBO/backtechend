package com.fin.tech.DTOS;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountDTO {
	
	
	

	
	private Integer id_user;
	
	
	private String name_account;
	

	private BigDecimal target_amount;
	

	private BigDecimal saved_amount;
	
	
	private LocalDate start_date;
	
	private LocalDate created_at;
	
	private String color_account;
	
	
	
	
	
	private BigDecimal amount;
	
	private Integer id_transact;
	private Integer id_account;
	private BigDecimal credit;
	private BigDecimal debit;
	
	
	
	



	public String getColor_account() {
		return color_account;
	}


	public void setColor_account(String color_account) {
		this.color_account = color_account;
	}


	public String getName_account() {
		return name_account;
	}


	public void setName_account(String name_account) {
		this.name_account = name_account;
	}


	public BigDecimal getCredit() {
		return credit;
	}


	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}


	public BigDecimal getDebit() {
		return debit;
	}


	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}


	public Integer getId_account() {
		return id_account;
	}


	public void setId_account(Integer id_account) {
		this.id_account = id_account;
	}


	public Integer getId_transact() {
		return id_transact;
	}


	public void setId_transact(Integer id_transact) {
		this.id_transact = id_transact;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	private LocalDate end_date;


	//public UsersModel getId_user() {
	//	return id_user;
	//}


	//public void setId_user(UsersModel id_user) {
	//	this.id_user = id_user;
	//}


	public Integer getId_user() {
		return id_user;
	}


	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}


	public LocalDate getCreated_at() {
		return created_at;
	}


	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}


/*	public String getname_account() {
		return name_account;
	}


	public void setname_account(String name_account) {
		this.name_account = name_account;
	}
	*/


	public BigDecimal getTarget_amount() {
		return target_amount;
	}


	public void setTarget_amount(BigDecimal target_amount) {
		this.target_amount = target_amount;
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


	public LocalDate getEnd_date() {
		return end_date;
	}


	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}
	
	
	
	
	
	
}
