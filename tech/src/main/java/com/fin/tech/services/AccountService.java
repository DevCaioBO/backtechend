package com.fin.tech.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fin.tech.DTOS.AccountDTO;
import com.fin.tech.repositorys.GoalRepository;

@Service
public class AccountService {
	
	@Autowired
	private GoalRepository goalRepository;
	
	//public List<GoalTransactionDTO> getGoalTransactions(){
	//	return goalRepository.findGoalTransactions();
	//}
	
	public List<AccountDTO> ListenerAccountTransactionDTO(Integer idGoal) throws Exception{
		
		
		return goalRepository.GetGoalTransaction(idGoal);
	}
	
	public Integer getIdOfOneUserService(String token) {
		
		Integer userId = goalRepository.getUserIdOfOneUser(token);
		
		return userId;
	}
	public List<AccountDTO> getTotalAmountOfOneUserRepo(Integer idUser) throws Exception{
		
		return goalRepository.getTotalAmountOfOneUserRepo(idUser);
	}
	
	public List<AccountDTO> getNameOfAccountsOfOneUser(Integer idUser) throws Exception{
		
		return goalRepository.getNameOfAccountsOfOneUser(idUser);
	}
	
}
