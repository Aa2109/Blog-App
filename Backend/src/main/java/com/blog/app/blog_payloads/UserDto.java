package com.blog.app.blog_payloads;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.UniqueElements;

import com.blog.app.blog_entity.Comments;
import com.blog.app.blog_entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
	
	@NotNull
	private int userId;
	
	
	@Size(min = 5, max = 20, message = "Name should be between 5 - 20 characters")
	@NotBlank
	private String name;

	@Email(message = "Enter a unique valid emailId")
	@Column(unique = true)
	@NotEmpty
	private String email;
	
	 @Size(
		min = 3, max = 12,
		 message= "Password should have a length between 5 and 12 characters.")
	 @NotBlank
	 @JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	//@NotEmpty(message = "About should not be empty..")
	@Size(min =10, max=200, message="About should contains atleast 10 charactes")
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<CommentDto> comments = new HashSet<>();
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<PostDto> posts = new HashSet<>();
	
}
