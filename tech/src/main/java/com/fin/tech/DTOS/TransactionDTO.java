package com.fin.tech.DTOS;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionDTO {
	
	private Integer id_accounts;
	private String name_account;
	private LocalDate created_at;
	private BigDecimal credit;
	private BigDecimal debit;
	private BigDecimal amount;
	private Integer id_transact;
	private String color_account;
	
	
	
	
	
	public String getColor_account() {
		return color_account;
	}
	public void setColor_account(String color_account) {
		this.color_account = color_account;
	}
	public Integer getId_transact() {
		return id_transact;
	}
	public void setId_transact(Integer id_transact) {
		this.id_transact = id_transact;
	}
	public String getName_account() {
		return name_account;
	}
	public void setName_account(String name_account) {
		this.name_account = name_account;
	}
	public Integer getId_accounts() {
		return id_accounts;
	}
	public void setId_accounts(Integer id_accounts) {
		this.id_accounts = id_accounts;
	}
	public LocalDate getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	

}
