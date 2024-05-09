package com.blog.app.blog_payloads;

import java.util.HashSet;
import java.util.Set;

import com.blog.app.blog_entity.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	
	private int categoryId;
	@Size(min = 5, max = 20, message = "Ctaegory name should be between 5 to 20 character")
	private String categoryName;
	@NotEmpty
	private String categoryDescription;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<PostDto> posts = new HashSet<>();

}
