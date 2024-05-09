package com.blog.app.blog_controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.app.blog_configuartion.AppConstants;
import com.blog.app.blog_payloads.ApiResponse;
import com.blog.app.blog_payloads.PostDto;
import com.blog.app.blog_payloads.PostResponse;
import com.blog.app.blog_services.PostServices;
import com.blog.app.blog_services.UploadFileServices;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostServices postServices ;
	@Autowired
	private UploadFileServices fileServices;
	
	@Value("${project.image}")
	private String path;
	
	// create
	@PostMapping("/user/{userId}/category/{categoryId}/create")
	public ResponseEntity<PostDto> creatPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		
		PostDto createdPost = this.postServices.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto dtos = this.postServices.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(dtos, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postServices.deletePost(postId);
		return new ResponseEntity(new ApiResponse("Category with id: "+postId+", deleted succeswsfully", true),HttpStatus.OK);
	}
	
//	@GetMapping("/allposts")
//	public ResponseEntity<List<PostDto>> findAllPost(){
//		List<PostDto> post = this.postServices.findAllPosts();
//		return new ResponseEntity<List<PostDto>>(post,HttpStatus.OK);
//	}
	
	@GetMapping("/post/pagination/posts")
	public ResponseEntity<PostResponse> findAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
		
		PostResponse postResponse = this.postServices.findAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> findPost(@PathVariable Integer postId){
		PostDto post = this.postServices.findSinglepost(postId);
		//System.out.println(">>>>>>>>>>>>>>  "+post.getComments()); 
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}
	
	// upload file
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadfile(@RequestParam("file") MultipartFile file, @PathVariable Integer postId) throws IOException{
	
			//file upload code
			PostDto postDto = this.postServices.findSinglepost(postId);
			String fileName = this.fileServices.uploadImage(path, file);
			postDto.setPostImage(fileName);
			PostDto updatedPost = this.postServices.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException{
		
		InputStream resource = this.fileServices.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
	
	
	@GetMapping("/search/post/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
		List<PostDto> res = this.postServices.findPostByKeyWord(keywords);
		return new ResponseEntity<List<PostDto>>(res,HttpStatus.FOUND);
	}
	
	// H/W--> implements pagination below two method also
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto> posts = this.postServices.findAllPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts = this.postServices.findAllPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}

}
