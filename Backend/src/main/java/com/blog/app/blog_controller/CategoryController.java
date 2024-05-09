package com.blog.app.blog_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.blog_payloads.ApiResponse;
import com.blog.app.blog_payloads.CategoryDto;
import com.blog.app.blog_services.CategoryServices;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryServices categoryServices;
	
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createUser(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto catDto = this.categoryServices.createCategory(categoryDto);
		return new ResponseEntity<>(catDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> updatecategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
		CategoryDto updatedCategory = this.categoryServices.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updatedCategory);
	}
	
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId){
		this.categoryServices.deleteCategory(categoryId);
		return new ResponseEntity(new ApiResponse("Category with id: "+categoryId+", deleted succeswsfully", true),HttpStatus.OK);
		
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(this.categoryServices.getCategoryById(categoryId));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDto>> getCatogories(){
		return ResponseEntity.ok(this.categoryServices.getAllCategories());
	}
	
	

}
