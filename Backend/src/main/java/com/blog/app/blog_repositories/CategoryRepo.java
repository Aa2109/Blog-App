package com.blog.app.blog_repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.blog_entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
