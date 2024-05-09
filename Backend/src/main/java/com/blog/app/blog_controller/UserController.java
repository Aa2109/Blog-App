package com.blog.app.blog_controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.blog_payloads.ApiResponse;
import com.blog.app.blog_payloads.UserDto;
import com.blog.app.blog_services.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	//private UserDto uDto;
	
	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto cretaedUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(cretaedUser,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") Integer userId){
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}
	

	@DeleteMapping("/delete/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId){
		this.userService.deleteUser(userId);
		//return new ResponseEntity(Map.of("message", "User deleted successfully"),HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User with id: "+userId+", deleted succeswsfully", true),HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		 List<UserDto> users = userService.getAllUsers();
		 for (UserDto user : users) {
		        System.out.println("User Name: " + user.getName()); // Use user email
		        // Other code
		    }
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}
