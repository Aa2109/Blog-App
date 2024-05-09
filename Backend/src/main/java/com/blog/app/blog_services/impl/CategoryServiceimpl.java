package com.blog.app.blog_services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.blog_entity.Category;
import com.blog.app.blog_entity.User;
import com.blog.app.blog_exceptions.ResourceNotFoundException;
import com.blog.app.blog_payloads.CategoryDto;
import com.blog.app.blog_payloads.UserDto;
import com.blog.app.blog_repositories.CategoryRepo;
import com.blog.app.blog_services.CategoryServices;

@Service
public class CategoryServiceimpl implements CategoryServices{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
//		Category category = this.dtoToCategory(categoryDto);
//		Category savedCategory = this.categoryRepo.save(category);
//		return this.categoryToDto(savedCategory);
		
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory,CategoryDto.class);
		
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		category.setCategoryName(categoryDto.getCategoryName());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category = this.categoryRepo.save(category);
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map(user->this.modelMapper.map(user,CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		this.categoryRepo.delete(category);
	}
	
	
	
//	private Category dtoToCategory(CategoryDto categoryDto) {
//		Category res = this.modelMapper.map(categoryDto, Category.class);
//		return res;
//	}
//	
//	private CategoryDto categoryToDto(Category category) {
//		CategoryDto res = this.modelMapper.map(category, CategoryDto.class);
//		return res;
//	}

}
