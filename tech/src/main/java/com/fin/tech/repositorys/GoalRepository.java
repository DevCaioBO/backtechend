package com.fin.tech.repositorys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fin.tech.DTOS.AccountDTO;
import com.fin.tech.models.UsersModel;
import com.fin.tech.services.JwtUtils;

@Repository
public class GoalRepository {
	
	//@PersistenceContext
	//private EntityManager entityManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private DataSource dataSource;
	
	//public List<GoalTransactionDTO> findGoalTransactions(){
		
	//	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		
	//	CriteriaQuery<GoalTransactionDTO> query = cb.createQuery(GoalTransactionDTO.class);
		
	//	Root<GoalsModel> goalRoot = query.from(GoalsModel.class);
		
	//	Join<GoalsModel, TransactionsModel> transactionJoin = goalRoot.join("transactions",JoinType.INNER);
		
	//	query.select(cb.construct(GoalTransactionDTO.class,goalRoot.get("id_accounts"),transactionJoin.get("id_transact")));
		
	//	TypedQuery<GoalTransactionDTO> typedQuery = entityManager.createQuery(query);
		
	//	return typedQuery.getResultList();
	//}
	
	public List<AccountDTO> GetGoalTransaction(Integer idGoal) throws Exception{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<AccountDTO> listObjAccount = new ArrayList<>();
		
		try {
			
			con = dataSource.getConnection();
			String sql = """
					SELECT * FROM accounts WHERE id_accounts = ?
					""";
			
			stmt  = con.prepareStatement(sql);
			
			stmt.setInt(1, idGoal);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				AccountDTO ObjAccount = new AccountDTO();
				
				ObjAccount.setName_account(rs.getString("name_account"));
				ObjAccount.setAmount(rs.getBigDecimal("amount"));
				ObjAccount.setCreated_at(rs.getDate("created_at").toLocalDate());
				
				listObjAccount.add(ObjAccount);
			}
			
		}
		finally {
			if(con!=null) {
				con.close();
			}
			if(stmt!=null) {
				stmt.close();
			}
			if(rs!=null) {
				rs.close();
			}
		}
		return listObjAccount;		
		
		
	}
	
	public Integer getUserIdOfOneUser(String token) {
		
		token = token.replace("Bearer ", "");
		Integer userId = jwtUtils.getUserIdFromToken(token);
		
		return userId;
	}
	
	public List<AccountDTO> getTotalAmountOfOneUserRepo(Integer userId) throws Exception{
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<AccountDTO> listenerGoalDto = new ArrayList<>();
		
		try {
			con = dataSource.getConnection();
			String sql = """
					select sum(t.amount) as amount_full from accounts a
					INNER JOIN transactions t 
					ON a.id_accounts = t.id_accounts 
					AND a.id_user =?;
					""";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, userId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				AccountDTO goalDto = new AccountDTO();
				
				goalDto.setAmount(rs.getBigDecimal("amount_full"));
				
				listenerGoalDto.add(goalDto);
			}
		}
		finally {
			if(stmt!=null) {
				stmt.close();
			}
			if(rs!=null) {
				rs.close();
			}
			if(con!=null) {
				con.close();
			}
		}
		return listenerGoalDto;
		
	}
	
	public List<AccountDTO> getNameOfAccountsOfOneUser(Integer userId) throws Exception{
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<AccountDTO> listenerGoalDto = new ArrayList<>();
	
		
		try {
			con = dataSource.getConnection();
			String sql = """
					SELECT a.id_user,a.id_accounts,t.created_at,t.credit,t.debit,a.name_account,sum(t.amount) as amount_full,t.id_transact from accounts a 
					INNER JOIN transactions t ON a.id_accounts = t.id_accounts 
					WHERE a.id_user =?
					GROUP BY a.id_user,a.id_accounts,t.created_at,t.credit,t.debit,t.id_transact,a.name_account ORDER BY t.id_transact  ;
					""";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, userId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				AccountDTO goalDto = new AccountDTO();
				
				
			
				goalDto.setId_user(rs.getInt("id_user"));
				goalDto.setId_account(rs.getInt("id_accounts"));
				goalDto.setCreated_at(rs.getDate("created_at").toLocalDate());
				goalDto.setCredit(rs.getBigDecimal("credit"));
				goalDto.setDebit(rs.getBigDecimal("debit"));
				goalDto.setAmount(rs.getBigDecimal("amount_full"));
				goalDto.setName_account(rs.getString("name_account"));
				goalDto.setAmount(rs.getBigDecimal("amount_full"));
				goalDto.setId_transact(rs.getInt("id_transact"));
				
				
				
				listenerGoalDto.add(goalDto);
			}
		}
		finally {
			if(stmt!=null) {
				stmt.close();
			}
			if(rs!=null) {
				rs.close();
			}
			if(con!=null) {
				con.close();
			}
		}
		return listenerGoalDto;
		
	}
	
	
	
}
