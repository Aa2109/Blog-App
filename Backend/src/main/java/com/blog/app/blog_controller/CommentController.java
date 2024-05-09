package com.blog.app.blog_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.blog_entity.Comments;
import com.blog.app.blog_payloads.ApiResponse;
import com.blog.app.blog_payloads.CommentDto;
import com.blog.app.blog_services.CommentServices;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentServices commentServices;

//	@PostMapping("/post/{postId}/user/{userId}")
//	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId, @PathVariable Integer userId){
//		CommentDto commentDtos = this.commentServices.createComment(commentDto, postId, userId);
//		return new ResponseEntity<CommentDto>(commentDtos, HttpStatus.CREATED);
//	}
	@PostMapping("/post/{postId}/create/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId, @RequestHeader("Authorization") String jwt){
		String trimmedJwt = jwt.substring(7);
//		System.out.println(jwt);
		CommentDto commentDtos = this.commentServices.createComment(commentDto, postId, trimmedJwt);
		return new ResponseEntity<CommentDto>(commentDtos, HttpStatus.CREATED); 
	}
	
	@DeleteMapping("/comments/delete/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentServices.deleteComment(commentId);
		return new ResponseEntity(new ApiResponse("Comment with id: "+commentId+", deleted succeswsfully", true),HttpStatus.OK);
	}
	
	@GetMapping("/comment/{id}")
	public ResponseEntity<CommentDto> getComment(@PathVariable Integer id){
		CommentDto dtos = this.commentServices.getSingleComment(id);
		return new ResponseEntity<CommentDto>(dtos,HttpStatus.OK);
	}
}
