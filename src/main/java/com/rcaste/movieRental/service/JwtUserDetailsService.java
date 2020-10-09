package com.rcaste.movieRental.service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rcaste.movieRental.models.Role;
import com.rcaste.movieRental.models.Users;
import com.rcaste.movieRental.repositories.RoleRepository;
import com.rcaste.movieRental.repositories.UsersRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
    private UsersRepository repository;
	
	@Autowired
    private RoleRepository repositoryR;
	
	/**
	 * Busca Usuario y carga rol
	 * @param usuario
	 * @return datos de usuario con roles
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users s = new Users();
		s=repository.findByUsername(username);
		List<Role> roles = repositoryR.findByUsers(s);
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase())));
		
		if (s == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
		return new org.springframework.security.core.userdetails.User(s.getUsername(), s.getPassword(),authorities);
		
		
	}
	
}
