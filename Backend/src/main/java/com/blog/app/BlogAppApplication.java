package com.blog.app;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.app.blog_configuartion.AppConstants;
import com.blog.app.blog_entity.Role;
import com.blog.app.blog_repositories.RoleRepo;


@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner{

//	private final PasswordEncoder passwordEncoder;
//
//    public BlogAppApplication(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args); 
	}
	@Bean
	ModelMapper modelMapper() {
	  return new ModelMapper();
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("xyz: "+this.passwordEncoder.encode("xyz"));
		try {
			Role role1 = new Role();
			role1.setRoleId(AppConstants.ADMIN_USER);
			role1.setRoleName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setRoleId(AppConstants.NORMAL_USER);
			role2.setRoleName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role1, role2);
			List<Role> saveAll = this.roleRepo.saveAll(roles);
			
			saveAll.forEach(r->{
				System.out.println(r.getRoleName());
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
