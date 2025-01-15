package com.fin.tech.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fin.tech.DTOS.AuthenticationDTO;
import com.fin.tech.DTOS.GoogleLoginRequest;
import com.fin.tech.DTOS.GoogleUserInfo;
import com.fin.tech.DTOS.JwtResponse;
import com.fin.tech.DTOS.LoginResponseDTO;
import com.fin.tech.DTOS.RegisterDTO;
import com.fin.tech.DTOS.UserDTO;
import com.fin.tech.ENUMS.UserRoles;
import com.fin.tech.models.UsersModel;
import com.fin.tech.repositorys.UsersRepository;

import com.fin.tech.services.JwtUtils;
import com.fin.tech.services.TokenService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("tech")
public class AuthorizationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired 
	UsersRepository repo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private TokenService tokenService;
	

   

	
	@PostMapping("/login")
	public ResponseEntity login(@Valid @RequestBody  AuthenticationDTO data){
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((UsersModel) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	
/*	@GetMapping("/user/credentials")
	public String loginSuccess(@AuthenticationPrincipal OAuth2User oauthUser) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    Object principal = authentication.getPrincipal();
		    System.out.println("Authentication principal: " + principal);

		    if (principal instanceof UsersModel) {
		        UsersModel user = (UsersModel) principal;
		        String username = user.getLogin();
		        String email = user.getEmail();
		        System.out.println("Username: " + username);
		        System.out.println("Email: " + email);


		        String jwtToken = tokenService.generateToken(user);
		        return "Login successful! JWT Token: " + jwtToken;
		    }


		    return "Error: Principal is not of type UsersModel.";
		}*/
	
	private final String uploadDirectory = "uploads/";
	
	@PostMapping("/register")
	public ResponseEntity register(@Valid @RequestBody RegisterDTO data) throws Exception{
		if(this.repo.findByLogin(data.getLogin())!=null) return ResponseEntity.badRequest().build();
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		//MultipartFile image_perfil = data.getImage_perfil();

		

		
		try {

			con = dataSource.getConnection();
			String sql = "INSERT INTO users(login,password,email,role) VALUES(?,?,?,?)";
			
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, data.getLogin());
			stmt.setString(2,encryptedPassword);
			stmt.setString(3, data.getEmail());
			stmt.setString(4, data.getRole().name());
			//stmt.setString(5, filename);
			
			stmt.executeUpdate();
		}finally {
			if(stmt!=null) {
				stmt.close();
			}
			if(con!=null) {
				con.close();
			}
		}
		
		
		
		return ResponseEntity.ok("pessoa criada com sucesso!");
	}
	
	/*@PostMapping("/google")
	public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) {
		Connection con = null;
		PreparedStatement stmt=null;
		ResultSet rs = null;
		UsersModel usersMDL =new UsersModel();
	    try {
	        GoogleUserInfo googleUserInfo = googleAuthService.validateGoogleToken(request.getToken());
	        String email = googleUserInfo.getEmail(); 
	        String name = googleUserInfo.getName();
	        String googleId = googleUserInfo.getGoogleId();

	     con = dataSource.getConnection();
	     String sql = """
	     		SELECT email from users WHERE email = ?
	     		""";
	     
	     stmt = con.prepareStatement(sql);
	     
	     stmt.setString(1, email);
	     
	     rs = stmt.executeQuery();
	     
	     if(!(rs.next())) {
	    	sql = """
	    			INSERT INTO users(email,login,googleid) VALUES(?,?,?)
	    			""";
	    	stmt = con.prepareStatement(sql);
	    	
	    	stmt.setString(1, email);
	    	stmt.setString(2, name);
	    	stmt.setString(3, googleId);
	    	
	    	stmt.executeUpdate();
	    	
	    	usersMDL.setEmail(email);
	    	usersMDL.setLogin(name);
	    	usersMDL.setGoogleId(googleId);
	     }

	       
	        String jwtToken = tokenService.generateToken(usersMDL);

	      
	        return ResponseEntity.ok(new JwtResponse(jwtToken));
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Token Google inválido");
	    }finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	     
	            e.printStackTrace();
	        }
	    }
	}
	*/
	/*
	@GetMapping("/oauth2/login")
	public ResponseEntity<String> oauthRegisterGoogleAndFacebook() throws Exception {
	
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    if (!(principal instanceof OAuth2User)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
	    }

	    OAuth2User oauth2User = (OAuth2User) principal;
	    String email = oauth2User.getAttribute("email");
	    String username = oauth2User.getAttribute("name");

	    System.out.println(email);
	    System.out.println(username);

	    String sqlQuery = "SELECT * FROM users WHERE email=?;";
	    String sqlInsert = "INSERT INTO users(login,email,role) VALUES(?,?,?);";

	    try (Connection con = dataSource.getConnection();
	         PreparedStatement stmt = con.prepareStatement(sqlQuery)) {

	        stmt.setString(1, email);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe na base");
	            }
	        }

	        try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert)) {
	            stmtInsert.setString(1, username);
	            stmtInsert.setString(2, email);
	            stmtInsert.setString(3, "USER");
	            stmtInsert.executeUpdate();
	        }


	        UsersModel newUser = new UsersModel();
	        newUser.setLogin(username);
	        newUser.setEmail(email);
	        newUser.setRole(UserRoles.USER);

	        String token = tokenService.generateToken(newUser);


	        return ResponseEntity.status(HttpStatus.CREATED)
	                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
	                .body("Usuário registrado com sucesso.");

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Erro interno no servidor: " + e.getMessage());
	    }
	}
*/
	
	public String makeUploadImage (MultipartFile image_perfil,Integer userId) throws Exception  {
		
		String currentFilename = null;
		Connection con = null;
		PreparedStatement stmt = null;
		String filename = null;
		try {
		con = dataSource.getConnection();
		String sql = "SELECT image_perfil FROM users WHERE id_user = ?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, userId);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
		    currentFilename = rs.getString("image_perfil");
		}

		
		
		if (currentFilename != null && !currentFilename.isEmpty()) {
		    File oldImage = new File(uploadDirectory + currentFilename);
		    if (oldImage.exists()) {
		        oldImage.delete();
		    }
		}
		
		String timestamp = String.valueOf(System.currentTimeMillis());
		 filename = timestamp + "_" + image_perfil.getOriginalFilename();
		String filepath = uploadDirectory + filename;
		
		File dir = new File(uploadDirectory);
		
		if(!dir.exists()) {
			dir.mkdirs();
		}
		Files.write(Paths.get(filepath), image_perfil.getBytes());
		}
		finally {
			if(stmt!=null) {
			stmt.close();
			}
			if(con!=null) {
				con.close();
			}
		}
		
		return filename;
	}

	public String TransformImageInBase64(String image_perfil_name) throws Exception {
		if(image_perfil_name != null && !image_perfil_name.isEmpty()) {
			String file_path = uploadDirectory + image_perfil_name;
			File image_file = new File(file_path);
			if(image_file.exists()) {
				byte[] image_bytes = Files.readAllBytes(Paths.get(file_path));
				String base64_image = Base64.getEncoder().encodeToString(image_bytes);
				return base64_image;
			}
			
			
				
			
		}
		
		return null;
		
	}
	@GetMapping("/collect")
	public ResponseEntity<?> getUserCredentials(@RequestHeader("Authorization") String token) throws Exception{
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		List<UserDTO> listenerOneUser = new  ArrayList<>();
		
		try {
			 token = token.replace("Bearer ", "");
			Integer userId = jwtUtils.getUserIdFromToken(token);
			
			con = dataSource.getConnection();
			String sql = """
					SELECT image_perfil,login,email,role from users WHERE id_user = ?;
					""";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				UserDTO OneUserCredentials = new UserDTO();
				
				String image_perfil_name = rs.getString("image_perfil");
				String image_base64 = TransformImageInBase64(image_perfil_name);
				
				OneUserCredentials.setImage_perfil(image_base64);
				OneUserCredentials.setLogin(rs.getString("login"));
				OneUserCredentials.setEmail(rs.getString("email"));
				String str_role = rs.getString("role");
				UserRoles str_rolestype = UserRoles.valueOf(str_role);
				OneUserCredentials.setRole(str_rolestype);
				
				listenerOneUser.add(OneUserCredentials);
				
			}
			
			
		}finally {
			if(con !=null) {
				con.close();
			}
			if(stmt!=null) {
				stmt.close();
			}
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(listenerOneUser);
	}
	
	@PutMapping("/updating")
	public ResponseEntity UpdatingUser(@Valid @ModelAttribute RegisterDTO data,@RequestHeader("authorization") String token) throws Exception{
		
		Connection con = null;
		PreparedStatement stmt = null;
		

		
		try {
			
			
			
			
			
			MultipartFile image_perfil = data.getImage_perfil();
			token = token.replace("Bearer ", "");
			
			Integer userId = jwtUtils.getUserIdFromToken(token);
			
			String filename = makeUploadImage(image_perfil,userId);
			
			con = dataSource.getConnection();
			String sql = """
					UPDATE users SET image_perfil = ? WHERE id_user = ?
					""";
			
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, filename);
			stmt.setInt(2, userId);
			
			stmt.executeUpdate();
			
			
		}
		finally {
			if(stmt!=null) {
				stmt.close();
			}
			if(con!=null) {
				con.close();
			}
		}
		
		return ResponseEntity.ok("Sua imagem foi atribuida a sua home!");
		
	}
	
	
}
