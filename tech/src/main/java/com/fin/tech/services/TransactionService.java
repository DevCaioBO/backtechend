package com.fin.tech.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fin.tech.DTOS.TransactionDTO;
import com.fin.tech.repositorys.TransactionRepository;

@Service
public class TransactionService {

	
	@Autowired
	private TransactionRepository repo;
	
	public Integer obtainIdAccount(Integer id_transact) throws Exception{
		
		return repo.obtainIdAccount(id_transact) ;
	}
	
	public Boolean generateTransaction(
			Integer id_accounts,
			Date created_at,
			BigDecimal credit,
			BigDecimal debit,
			BigDecimal amount) 
			throws Exception{
			
		return repo.generateTransact(id_accounts, created_at, credit, debit, amount);
	}
	
	public List<TransactionDTO> ObtainFullCreditAndDebit(Integer idUser)throws Exception {
		
		return repo.ObtainFullCreditAndDebit(idUser);
		
	}
	
	
	public List<TransactionDTO> ObtainFullCreditAndDebitPerAccount(Integer idUser)throws Exception {
		
		return repo.ObtainFullCreditAndDebitPerAccount(idUser);
		
	}
	public List<TransactionDTO> ObtainTransactionsPerAccount(Integer userId,Integer accountId) throws Exception {
		return repo.ObtainTransactionsPerAccount(userId, accountId);
	}
}
