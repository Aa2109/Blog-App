package com.blog.app.blog_payloads;

import java.util.*;

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
	private UserDto user;
	private List<CommentDto> comments = new ArrayList<>();
}
