package com.blog.app.blog_repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.app.blog_entity.Category;
import com.blog.app.blog_entity.Post;
import com.blog.app.blog_entity.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	@Query("select p from Post p where p.postTitle like:key")
	List<Post> findByPostTitle(@Param("key") String title);
	

}
