package com.fin.tech.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fin.tech.ENUMS.EnumTransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name="transactions")
@Table(name="transactions")
public class TransactionsModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_transact")
	private Integer id_transact;
	
	//@ManyToOne
	//@JoinColumn(name="id_user",nullable=false)
	//private UsersModel id_user;
	
    @ManyToOne
    @JoinColumn(name = "id_accounts")  
    private AccountsModel accounts;
    
    
    @Column(name="credit")
    private BigDecimal credit;
    
    @Column(name="debit")
    private BigDecimal debit;
    

	private UsersModel id_user;
	
	
	
	//@ManyToOne
	//@JoinColumn(name="id_category",nullable=false)
	//private CategoryModel category;
	
	@Column(name="amount",precision=10,scale=2)
	private BigDecimal amount;
	
	//@Column(name="transaction_type",nullable=false)
	//@Enumerated(EnumType.STRING)
	//private EnumTransactionType type;
	
	//@Column(name="descriptions")
	//private String descriptions;
	
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt = LocalDate.now();

	public Integer getId_transact() {
		return id_transact;
	}

	public void setId_transact(Integer id_transact) {
		this.id_transact = id_transact;
	}


	public AccountsModel getGoal() {
		return accounts;
	}

	public void setGoal(AccountsModel accounts) {
		this.accounts = accounts;
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

	public UsersModel getId_user() {
		return id_user;
	}

	public void setId_user(UsersModel id_user) {
		this.id_user = id_user;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	
	
	
}
