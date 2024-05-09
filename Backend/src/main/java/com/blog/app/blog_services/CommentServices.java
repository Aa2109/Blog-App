package com.blog.app.blog_services;

import com.blog.app.blog_payloads.CommentDto;

public interface CommentServices {
	
	CommentDto createComment(CommentDto commentDto, Integer postId, String jwt);
	CommentDto getSingleComment(Integer id);
//	CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
	void deleteComment(Integer commentId);

}
