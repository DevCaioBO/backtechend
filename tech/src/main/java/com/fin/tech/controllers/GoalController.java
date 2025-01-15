package com.fin.tech.controllers;

import java.math.BigDecimal;
import java.security.Provider.Service;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fin.tech.DTOS.AccountDTO;
import com.fin.tech.DTOS.UserAccountDTO;
import com.fin.tech.ENUMS.UserRoles;
import com.fin.tech.services.AccountService;
import com.fin.tech.services.JwtUtils;

@RestController
@RequestMapping("account")
public class GoalController {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private AccountService goalService;
	
	
    @Autowired  
    private JwtUtils jwtUtils;
    
    private Boolean doTransation(int idAccount,Date dataNow,BigDecimal credit,BigDecimal debit,BigDecimal amount)throws Exception {
    	Connection con = null;
    	PreparedStatement stmt = null;
    	
    	try {
    		con = dataSource.getConnection();
    		String sql = """
    				INSERT transactions(id_accounts,created_at,credit,debit,amount) VALUES(?,?,?,?,?)
    				""";
    		stmt = con.prepareStatement(sql);
    		stmt.setInt(1, idAccount);
    		stmt.setDate(2, dataNow);
    		stmt.setBigDecimal(3, credit);
    		stmt.setBigDecimal(4, debit);
    		stmt.setBigDecimal(5, amount);
    		
    		stmt.executeUpdate();
    		
    	}
    
    	
    	finally{
    		if(stmt!=null) {
    			stmt.close();
    		}if(con!=null) {
    			con.close();
    		}
    		
    	}
    	return true;
    }
	public Boolean doAccount(Date data,String colorAccount,String nameAccount,BigDecimal sale,Integer userId) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		


		try {
			con = dataSource.getConnection();
		
			String sql = "INSERT INTO accounts(id_user,color_account,name_account,amount,created_at) VALUES(?,?,?,?,?)";
			
			
			
			
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setString(2, colorAccount);
			stmt.setString(3, nameAccount);
			stmt.setBigDecimal(4, sale);
			stmt.setDate(5, data);
			
			
			
			
			
			
			
			
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected == 0) {
			    System.out.println("Nenhuma linha foi afetada pela inserção.");
			} else {
			    System.out.println("Inserção bem-sucedida.");
			}
			
			
		}
		//catch(Exception e) {
		//	return false;
		//}
		finally {
			if(stmt!=null){
				stmt.close();
			}
			if(con!=null) {
				con.close();
			}
			
			}
		return true;
		
		
		
	}
	

	
	private Integer getLastIdFromGoals() throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		Integer Lastid = 0;
		
		try {
			con = dataSource.getConnection();
			String sql = """
					 select last_insert_id() as last_id from accounts;
					""";
			stmt = con.prepareStatement(sql);
			
			ResultSet rs  = stmt.executeQuery();
			
			while(rs.next()) {
				Lastid = rs.getInt("last_id");
			}
			
		}catch(Exception e){
			return Lastid;
		}finally {
			if(stmt!=null) {
				stmt.close();
			}
			if(con!=null) {
				con.close();
			}
			
		}
		return Lastid;
	}
	@PostMapping("create")
	public ResponseEntity<?> PostAccount(@RequestBody AccountDTO data,@RequestHeader("Authorization") String token) throws Exception {
		
		Connection con = null;
		try {
			token = token.replace("Bearer ", "");
			Integer userId = jwtUtils.getUserIdFromToken(token);
			
			con = dataSource.getConnection();
			
			con.setAutoCommit(false);
			
			doAccount(java.sql.Date.valueOf(data.getCreated_at()),data.getColor_account(),data.getName_account(),data.getAmount(), userId);
			Integer lastId = getLastIdFromGoals();
			doTransation(lastId,java.sql.Date.valueOf(data.getCreated_at()),data.getCredit(),data.getDebit(), data.getAmount());
			
			List<AccountDTO> account = goalService.ListenerAccountTransactionDTO(lastId);
			con.commit();
			
			return ResponseEntity.status(HttpStatus.CREATED).body(account);
		}
		catch(Exception e) {
			if(con!=null) {
				try {
					con.rollback();
				}catch(SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("erro ao fazer transação");
		}
		finally {
			if(con!=null) {
				try {
					con.close();
				}catch(SQLException closeEx) {
					closeEx.printStackTrace();
				}
			}
		}
		
	}
	
	@GetMapping("/total")
	public ResponseEntity<?> getTotalAmountOfOneUserRepo(@RequestHeader("Authorization") String token)throws Exception{
		
		List<AccountDTO> totalAmount = goalService.getTotalAmountOfOneUserRepo(goalService.getIdOfOneUserService(token)); 
		
		return ResponseEntity.ok().body(totalAmount);
	}
	
	@GetMapping("/info")
	public ResponseEntity<?> getNameOfAccountsOfOneUser(@RequestHeader("Authorization") String token)throws Exception{
		
		List<AccountDTO> infoAccount = goalService.getNameOfAccountsOfOneUser(goalService.getIdOfOneUserService(token)); 
		
		return ResponseEntity.ok().body(infoAccount);
	}
	
	
	@GetMapping("collect")
	public ResponseEntity<?> GetGoal(@RequestHeader("Authorization") String token) throws Exception{
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		List<UserAccountDTO> userGoal = new ArrayList<>();
		
		
		
		try {
			
			con = dataSource.getConnection();
			
			String sql = """
					SELECT u.id_user,u.login,u.role,g.id_accounts,g.created_at,g.name_account
					FROM users u
					INNER JOIN accounts g ON u.id_user = g.id_user AND u.id_user = ?
					""" ;
			
			token = token.replace("Bearer ", "");
			
			
			Integer userId = jwtUtils.getUserIdFromToken(token);
			LocalDate createdAt = LocalDate.now();
			
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				UserAccountDTO goals = new UserAccountDTO();
				
				goals.setId_user(rs.getInt("id_user"));
				goals.setLogin(rs.getString("login"));
				
				String ToStringRole = rs.getString("role");
				UserRoles EnumRole =  UserRoles.valueOf(ToStringRole);
				goals.setRole(EnumRole);
				
				goals.setid_accounts(rs.getInt("name_accounts"));
				
				goals.setCreated_at(rs.getDate("created_at").toLocalDate());
				
				goals.setEnd_date(rs.getDate("end_date").toLocalDate());
				
				goals.setname_accounts(rs.getString("name_account"));
				

				
				userGoal.add(goals);
			}
			
		}
		finally{
			if(stmt!=null) {
				stmt.close();
			}
			if(con !=null) {
				con.close();
			}
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userGoal);
		
	}
	
}
