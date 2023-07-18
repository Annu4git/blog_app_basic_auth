package com.anurag.blogapp.service;

import com.anurag.blogapp.Dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);

    public CategoryDto getCategory(int categoryId);

    public void deleteCategory(int categoryId);

    public List<CategoryDto> getAllCategories();
}
