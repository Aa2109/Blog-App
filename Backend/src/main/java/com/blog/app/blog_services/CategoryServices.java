package com.blog.app.blog_services;

import java.util.List;
import com.blog.app.blog_payloads.CategoryDto;

public interface CategoryServices {

	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto userDto, Integer categoryId);
	CategoryDto getCategoryById(Integer categoryId);
	List<CategoryDto> getAllCategories();
	void deleteCategory(Integer categoryId);
}
