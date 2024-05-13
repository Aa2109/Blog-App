package com.blog.app.blog_payloads;

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
//	private PostDto post;
	 private int postId;
}
