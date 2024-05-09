package com.blog.app.blog_entity;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(exclude = {"comments"})// Exclude collections to avoid circular dependencies in hashCode
@Entity
@Data
@NoArgsConstructor
@Table(name = "PostTable")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	@Column(name="post_title", length = 500, nullable = false)
	private String postTitle;
	private String postContent;
	private String postImage;
	private Date postDate;
	
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
	
	
//	@JoinColumn(name = "userId", nullable = false) //User associations (@ManyToOne) have been set to nullable =
						//false, which means every post must have a user associated with it.
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user; 
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comments> comments = new HashSet<>();

	
}
