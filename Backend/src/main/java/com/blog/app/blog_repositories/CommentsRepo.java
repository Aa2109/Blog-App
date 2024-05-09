package com.blog.app.blog_repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.blog_entity.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer>{

}
