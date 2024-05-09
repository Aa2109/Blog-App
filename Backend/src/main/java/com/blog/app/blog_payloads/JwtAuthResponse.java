package com.blog.app.blog_payloads;

import com.blog.app.blog_entity.User;

import lombok.Builder;
import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	private UserDto user;
}
