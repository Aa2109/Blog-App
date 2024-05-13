package com.blog.app.blog_services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.blog.app.blog_entity.Comments;
import com.blog.app.blog_entity.Post;
import com.blog.app.blog_entity.User;
import com.blog.app.blog_exceptions.ResourceNotFoundException;
import com.blog.app.blog_payloads.CommentDto;
import com.blog.app.blog_repositories.CommentsRepo;
import com.blog.app.blog_repositories.PostRepo;
import com.blog.app.blog_services.CommentServices;
import com.blog.app.security.CustomUserDetailService;
import com.blog.app.security.JwtTokenHelper;

@Service
public class CommentServicesImpl implements CommentServices {

	@Autowired
	private PostRepo postRepo;
//	@Autowired
//	private UserRepo userRepo;
	@Autowired
	private CommentsRepo commentsRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private CustomUserDetailService userDetailsService;

//	@Override
//	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
//		
//		Post post =this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
//		
//		User user = this.userRepo.findById(userId).orElseThrow((()-> new ResourceNotFoundException("User", "userId", userId)));
//		
//		Comments comments =this.modelMapper.map(commentDto, Comments.class);
//		comments.setPost(post);
//		comments.setUser(user);
//		Comments savedComment = this.commentsRepo.save(comments);
//		
//		return this.modelMapper.map(savedComment, CommentDto.class);
//	}
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, String jwt) {

		Post post = this.postRepo.findById(postId) 
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post ID", postId));

		Comments comment = this.modelMapper.map(commentDto, Comments.class);
//		comment.setPost(post);
		String username = this.jwtTokenHelper.getUsernameFromJwtToken(jwt);
//		System.out.println("username: "+username);
		UserDetails user = this.userDetailsService.loadUserByUsername(username);
		comment.setPost(post);
		comment.setUser((User) user);
		
		Comments savedComment = this.commentsRepo.save(comment);
		
		CommentDto dtos =  this.modelMapper.map(savedComment, CommentDto.class);
//		System.out.println(savedComment.getPost().getPostTitle() + "........."+savedComment.getUser().getName());
		return dtos;
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comments com = this.commentsRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Commen ID", commentId));
		this.commentsRepo.delete(com);
	}

	@Override
	public CommentDto getSingleComment(Integer id) {
		Comments com = this.commentsRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentID", id));
		
		return this.modelMapper.map(com, CommentDto.class);
	}

}
