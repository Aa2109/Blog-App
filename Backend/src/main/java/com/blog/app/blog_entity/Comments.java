package com.blog.app.blog_entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;


//@EqualsAndHashCode(exclude = {"post"})// Exclude Post to avoid circular dependencies in hashCode
@Entity
@Data
public class Comments {
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentId;
	
	private String commentContent;
	
	@ManyToOne
	private Post post;
	
	@ManyToOne
	private User user;

}
