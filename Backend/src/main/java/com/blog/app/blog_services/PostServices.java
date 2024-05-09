package com.blog.app.blog_services;

import java.util.List;

import com.blog.app.blog_entity.Post;
import com.blog.app.blog_payloads.PostDto;
import com.blog.app.blog_payloads.PostResponse;

public interface PostServices {
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	void deletePost(Integer postId);
	
//	List<PostDto> findAllPosts();
	
	PostResponse findAllPosts(Integer pageNumber, Integer pageSize,String sortBy, String sortDir);
	
	PostDto findSinglepost(Integer postId);
	
	List<PostDto> findAllPostByCategory(Integer categoryId);
	
	List<PostDto> findAllPostByUser(Integer userId);
	
	List<PostDto> findPostByKeyWord(String string);
}
