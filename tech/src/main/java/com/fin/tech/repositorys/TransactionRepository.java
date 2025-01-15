package com.fin.tech.repositorys;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fin.tech.DTOS.TransactionDTO;

@Repository
public class TransactionRepository {
	@Autowired
	private DataSource dataSource;
	
	public Integer obtainIdAccount(Integer id_transact) throws Exception {
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Integer idAccount = null;
		try {
			con = dataSource.getConnection();
			String sql = """
					SELECT id_accounts FROM transactions WHERE id_transact =?
					""";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id_transact);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				idAccount = rs.getInt("id_accounts");
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("Erro ao obter id da conta TransactionRepository -  line:46");
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
	  return idAccount;
	}
	
	public Boolean generateTransact(Integer id_accounts,Date created_at,BigDecimal credit,BigDecimal debit,BigDecimal amount) throws Exception{
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			con = dataSource.getConnection();
			String sql = """
					INSERT INTO transactions(id_accounts,created_at,credit,debit,amount) VALUES(?,?,?,?,?)
					""";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1,id_accounts);
			stmt.setDate(2, created_at);
			stmt.setBigDecimal(3, credit);
			stmt.setBigDecimal(4, debit);
			stmt.setBigDecimal(5, amount);
			stmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("Erro ao tentar gerar a transação TransactionRepository -  line:82");
		}
		finally {
			if(con!=null) {
				con.close();
			}
			if(stmt!=null) {
				stmt.close();
			}
		}
		
		return true;
		
	}
	//Obtendo Total depositado e total retirado por conta
	
	public List<TransactionDTO> ObtainFullCreditAndDebit(Integer idUser) throws Exception{
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<TransactionDTO> listFullEntryQuit = new ArrayList<>();
		
		try {
			con = dataSource.getConnection();
			String sql="""
					SELECT a.id_user,SUM(t.credit) total_credit,SUM(t.debit) total_debit FROM users u
					INNER JOIN accounts a ON a.id_user = u.id_user
					INNER JOIN transactions t ON a.id_accounts = t.id_accounts
					WHERE a.id_user = ?
					GROUP BY a.id_user;
					""";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, idUser);
			
			rs  = stmt.executeQuery();
			
			while(rs.next()) {
				TransactionDTO EntryQuit = new TransactionDTO();
				
				EntryQuit.setCredit(rs.getBigDecimal("total_credit"));
				EntryQuit.setDebit(rs.getBigDecimal("total_debit"));
				
				listFullEntryQuit.add(EntryQuit);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("Erro ao tentar obter total de credit e debit de um usuário line:112");
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
		return listFullEntryQuit;
		
		
	}
	
	public List<TransactionDTO> ObtainFullCreditAndDebitPerAccount(Integer idUser) throws Exception{
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<TransactionDTO> listFullEntryQuit = new ArrayList<>();
		
		try {
			con = dataSource.getConnection();
			String sql="""
					SELECT a.id_user,a.id_accounts,a.color_account,a.name_account, sum(t.credit) total_credit, sum(t.debit)   total_debit
					from users u
					INNER JOIN accounts a ON u.id_user = a.id_user 
					INNER JOIN transactions t ON a.id_accounts = t.id_accounts 
					WHERE a.id_user =?
					GROUP BY a.id_user,a.id_accounts,a.name_account,a.color_account;
					""";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, idUser);
			
			rs  = stmt.executeQuery();
			
			while(rs.next()) {
				TransactionDTO EntryQuit = new TransactionDTO();
				EntryQuit.setId_accounts(rs.getInt(("id_accounts")));
//				EntryQuit.setId_transact(rs.getInt("id_transact"));
				EntryQuit.setColor_account(rs.getString("color_account"));
				EntryQuit.setName_account(rs.getString("name_account"));
				EntryQuit.setCredit(rs.getBigDecimal("total_credit"));
				EntryQuit.setDebit(rs.getBigDecimal("total_debit"));
				
				listFullEntryQuit.add(EntryQuit);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("Erro ao tentar obter total de credit e debit de um usuário line:112");
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
		return listFullEntryQuit;
		
		
	}
	
	public List<TransactionDTO> ObtainTransactionsPerAccount(Integer userId,Integer accountId) throws Exception{
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<TransactionDTO> listOfTransactionsOfOneAccount = new ArrayList<>();
		
		try {
			con = dataSource.getConnection();
			String sql="""
					select t.id_transact,a.name_account,t.created_at, t.credit credit,t.debit debit,t.amount amount 
					from transactions t 
					INNER JOIN accounts a  ON t.id_accounts = a.id_accounts
					INNER JOIN users u ON u.id_user = a.id_user
					where a.id_accounts =? and u.id_user = ?;
					""";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, accountId);
			stmt.setInt(2, userId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				TransactionDTO transactionAccount = new TransactionDTO();
				
				transactionAccount.setId_transact(rs.getInt("id_transact"));
				transactionAccount.setName_account(rs.getString("name_account"));
				transactionAccount.setCreated_at(rs.getDate("created_at").toLocalDate());
				transactionAccount.setCredit(rs.getBigDecimal("credit"));
				transactionAccount.setDebit(rs.getBigDecimal("debit"));
				transactionAccount.setAmount(rs.getBigDecimal("amount"));
				
				listOfTransactionsOfOneAccount.add(transactionAccount);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("Algo deu errado na tentiva de obter as transações dessa conta line:225 TransactionRepository");
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
		return listOfTransactionsOfOneAccount;
		
		
		
		
	}
	
	
	
}
