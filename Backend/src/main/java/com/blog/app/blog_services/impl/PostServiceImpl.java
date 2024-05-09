package com.blog.app.blog_services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.blog.app.blog_entity.Category;
import com.blog.app.blog_entity.Post;
import com.blog.app.blog_entity.User;
import com.blog.app.blog_exceptions.ResourceNotFoundException;
import com.blog.app.blog_payloads.PostDto;
import com.blog.app.blog_payloads.PostResponse;
import com.blog.app.blog_repositories.CategoryRepo;
import com.blog.app.blog_repositories.PostRepo;
import com.blog.app.blog_repositories.UserRepo;
import com.blog.app.blog_services.PostServices;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostServices{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
				
	    Post post = this.modelMapper.map(postDto, Post.class);
	    post.setPostImage("default.png");
	    post.setPostDate(new Date());
	    post.setUser(user);
	    post.setCategory(category);
	
		Post newPost= this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("pst", "Postid", postId));
		Category cat = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();
		post.setPostContent(postDto.getPostContent());
		post.setPostTitle(postDto.getPostTitle());
		post.setPostImage(postDto.getPostImage());
		post.setCategory(cat);
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.delete(post);
	}

	
//	public List<PostDto> findAllPosts() {
//	  List<Post> posts =  this.postRepo.findAll();
//	  List<PostDto> allPosts = posts.stream().map(post-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//	  return allPosts;
//	}
	
	public PostResponse findAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		
		PageRequest p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		
		List<Post> posts =  pagePost.getContent();
		
		List<PostDto> allPosts = posts.stream().map(post-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPosts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages()-1);
		postResponse.setLastPage(pagePost.isLast());
		
		
		return postResponse;
	}

	@Transactional
	public PostDto findSinglepost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	
	@Override
	public List<PostDto> findAllPostByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postdtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postdtos;
	}

	@Override
	public List<PostDto> findAllPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postdtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postdtos;
		
	}

	@Override
	public List<PostDto> findPostByKeyWord(String string) {
		List<Post> posts = this.postRepo.findByPostTitle("%"+string+"%");
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
