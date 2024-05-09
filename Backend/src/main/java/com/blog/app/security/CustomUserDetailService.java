package com.blog.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.app.blog_repositories.UserRepo;
import com.blog.app.blog_entity.User;
import com.blog.app.blog_exceptions.ResourceNotFoundException;

@Service
@EnableWebMvc
public class CustomUserDetailService implements UserDetailsService {
	
	
	@Autowired
	private UserRepo userRepo;
//	 private final UserRepo userRepo;
//
//	    public CustomUserDetailService(UserRepo userRepo) {
//	        this.userRepo = userRepo;
//	    }

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Loading user from DB by userName
		User user = this.userRepo.findByEmail(email).orElseThrow(()->(new ResourceNotFoundException("Email","Email-ID",email)));
		return user;
	}

	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(this).passwordEncoder(passwordEncoder());
	}

    @Bean
    PasswordEncoder passwordEncoder(){
		return new  BCryptPasswordEncoder();
	}

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
