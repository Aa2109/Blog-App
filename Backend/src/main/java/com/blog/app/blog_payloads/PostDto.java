package com.blog.app.blog_payloads;

import java.util.*;
import com.blog.app.blog_entity.Comments;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	
	private int postId;
	@Column(nullable = false)
	private String postTitle;
	private String postContent;
	private String postImage;
	private Date postDate;
	private CategoryDto category;
//	@JsonProperty(access = Access.WRITE_ONLY)
	private UserDto user;
//	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<CommentDto> comments = new HashSet<>();
}
