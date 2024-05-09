package com.blog.app.blog_services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.app.blog_configuartion.AppConstants;
import com.blog.app.blog_entity.Role;
import com.blog.app.blog_entity.User;
import com.blog.app.blog_exceptions.ResourceNotFoundException;
import com.blog.app.blog_payloads.UserDto;
import com.blog.app.blog_repositories.RoleRepo;
import com.blog.app.blog_repositories.UserRepo;
import com.blog.app.blog_services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto); 
//		String userPassword = user.getUserPassword();
//		String encodedPass = this.passwordEncoder.encode(userPassword);
		User savedUser =this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->(new ResourceNotFoundException("User","id",userId)));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
//		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		User updatedUser = this.userRepo.save(user);
		
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->(new ResourceNotFoundException("User","id",userId)));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->(new ResourceNotFoundException("User","id",userId)));
		user.getRoles().clear();
		this.userRepo.delete(user);
	}
	
	
	private User dtoToUser(UserDto userDto) {
		User user =this.modelMapper.map(userDto, User.class);
		/*
		User user =new User();
		user.setU_id(userDto.getU_id());
		user.setU_name(userDto.getU_name());
		user.setU_email(userDto.getU_email());
		user.setU_password(userDto.getU_password());
		user.setU_about(userDto.getU_about());
		*/
		return user;
	}
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		/*
		UserDto userDto =new UserDto();
		userDto.setU_id(user.getU_id());
		userDto.setU_name(user.getU_name());
		userDto.setU_email(user.getU_email());
		userDto.setU_password(user.getU_password());
		userDto.setU_about(user.getU_about());
		*/
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		//password encode
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
//		System.out.println(user.getPassword());
		//Roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
