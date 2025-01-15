package com.fin.tech.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.fin.tech.models.UsersModel;

public interface UsersRepository extends JpaRepository<UsersModel,Integer> {
	UserDetails findByLogin(String login);
}
