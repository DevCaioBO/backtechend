package com.fin.tech.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity(name="accounts")
@Table(name="accounts")
public class AccountsModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_accounts;
	
	@ManyToOne
	@JoinColumn(name="id_user")
	private UsersModel id_user;
	
	@Column(name="amount",precision=10,scale=2)
	private BigDecimal amount;
	
	@Column(name="name_account")
	private String name_account;
	
	@Column(name="color_account")
	private String colorAccount;
	
	
	
	
    public String getColorAccount() {
		return colorAccount;
	}

	public void setColorAccount(String colorAccount) {
		this.colorAccount = colorAccount;
	}

	@OneToMany(mappedBy = "accounts",cascade = CascadeType.ALL)  
    private List<TransactionsModel> transactions;
	
    @Column(name = "created_at",nullable=false)
    private LocalDate created_at;
    

	public Integer getId_accounts() {
		return id_accounts;
	}

	public void setId_accounts(Integer id_accounts) {
		this.id_accounts = id_accounts;
	}

	public UsersModel getUser() {
		return id_user;
	}

	public void setUser_id(UsersModel id_user) {
		this.id_user = id_user;
	}

	public String getname_account() {
		return name_account;
	}

	public void setname_account(String name_account) {
		this.name_account = name_account;
	}

	public LocalDate getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}
    
    
}
