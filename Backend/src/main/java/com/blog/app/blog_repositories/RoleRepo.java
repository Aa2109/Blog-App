package com.blog.app.blog_repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.blog_entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
