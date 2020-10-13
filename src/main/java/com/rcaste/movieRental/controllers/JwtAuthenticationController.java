package com.rcaste.movieRental.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rcaste.movieRental.config.JwtTokenUtil;
import com.rcaste.movieRental.models.Role;
import com.rcaste.movieRental.models.Users;
import com.rcaste.movieRental.models.requests.JwtRequest;
import com.rcaste.movieRental.models.requests.JwtResponse;
import com.rcaste.movieRental.repositories.RoleRepository;
import com.rcaste.movieRental.repositories.UsersRepository;
import com.rcaste.movieRental.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    private UsersRepository repository;
	
	@Autowired
    private RoleRepository repositoryR;
	
	/**
	 * Autentica y crea TOKEN
	 * @param authenticationRequest JSON de entrada con datos a autenticar
	 * @return JSON con TOKEN 
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	/**
	 * Metodo de prueba que crea 2 usuarios uno admin y otro USER para pruebas
	 * @return confirmacion de usuarios creados
	 */
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String createInitUsers() {
		
		repository.saveAndFlush(returnAdmin());
		repository.saveAndFlush(returnUser());
		
		return "Test Users Created";
	}
	
	/**
	 * Metodo de confirmacion de servicio disponible
	 * @return mensaje de confirmacion
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String status() {

		return "Movie Rental App is online";
	}
	
	/**
	 * Creacion de usuario Admin
	 * @return Usuario Admin que sera ingresado
	 */
	private Users returnAdmin() {
		
		Optional<Role> role = repositoryR.findById((long) 1);
		 
		
		Users admin = new Users();
		admin.setName("admin");
		admin.setEmail("admin@gmail.com");
		admin.setPassword(bCryptPasswordEncoder.encode("admin"));
		admin.setUsername("admin");
		admin.setRole(role.get());
		
		return admin;
	}
	
	/**
	 * Creacion de usuario USER
	 * @return usuario User que sera ingresado
	 */
	private Users returnUser() {
		
		Optional<Role> role = repositoryR.findById((long) 2);
		
		Users admin = new Users();
		admin.setName("user");
		admin.setEmail("user@gmail.com");
		admin.setPassword(bCryptPasswordEncoder.encode("user"));
		admin.setUsername("user");
		admin.setRole(role.get());
		
		return admin;
	}
		
/**
 * Autentica usuario en base a usuario y pwd
 * @param username usuario
 * @param password contraseña
 * @throws Exception
 */
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
}
