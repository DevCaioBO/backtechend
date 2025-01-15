package com.fin.tech.models;

import java.util.List;

import com.fin.tech.ENUMS.EnumCategoryType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name="categorys")
@Table(name="categorys")
public class CategoryModel {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_category")
	private Integer id_category;
	
	@Column(name="name_category")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name="type_category",nullable=false)
	private EnumCategoryType type;
	
	@OneToMany(mappedBy="id_transact")
	private List<TransactionsModel> transactions;
	
}
