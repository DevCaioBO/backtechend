package com.fin.tech.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fin.tech.DTOS.TransactionDTO;
import com.fin.tech.services.AccountService;
import com.fin.tech.services.TransactionService;

@RestController
@RequestMapping("transaction")
public class TransactionController {
	
	
	@Autowired
	private TransactionService service;
	
	@Autowired
	private AccountService serviceAccount;
	
	@PostMapping("create")
	public Boolean generateTransaction(@RequestBody TransactionDTO data) throws Exception {
		
		return service.generateTransaction
				(data.getId_accounts(),
						java.sql.Date.valueOf(data.getCreated_at()),
						data.getCredit(),
						data.getDebit(),
						data.getAmount());
	}
	//COLETA O TOTAL DE DEBITO E CREDITO POR USUÁRIO
	@GetMapping("collect")
	public List<TransactionDTO> ObtainFullCreditAndDebit(@RequestHeader("Authorization") String token) throws Exception{
		
		return service.ObtainFullCreditAndDebit(serviceAccount.getIdOfOneUserService(token));
	}
	
	//COLETA O TOTAL DE DEBITO E CREDITO POR CONTA
	@GetMapping("collect/per/account")
	public List<TransactionDTO> ObtainFullCreditAndDebitPerAccount(@RequestHeader("Authorization") String token) throws Exception{
		
		return service.ObtainFullCreditAndDebitPerAccount(serviceAccount.getIdOfOneUserService(token));
	}
	
	@GetMapping("/obtain/per/account/{id_account}")
	public List<TransactionDTO> ObtainTransactionsPerAccount (@PathVariable Integer id_account,@RequestHeader("Authorization") String token) throws Exception{
		return service.ObtainTransactionsPerAccount(serviceAccount.getIdOfOneUserService(token), id_account);
	}
	
	
}
