package com.blog.app.blog_payloads;
import com.blog.app.blog_entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto{ 
	private int commentId;
	private String commentContent;	
	private UserDto user;
	private PostDto posts;
}
