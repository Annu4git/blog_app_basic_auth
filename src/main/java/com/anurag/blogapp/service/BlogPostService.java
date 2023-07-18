package com.anurag.blogapp.service;

import com.anurag.blogapp.Dto.BlogPostDto;
import com.anurag.blogapp.Dto.BlogPostResponse;

import java.util.List;

public interface BlogPostService {

    public BlogPostDto createBlogPost(BlogPostDto blogPost, int userId, int categoryId);

    public BlogPostDto updateBlogPost(BlogPostDto blogPost, int postId, int categoryId);

    public void deleteBlogPost(int postId);

    public BlogPostDto getBlogPost(int postId);

    public BlogPostResponse getAllBlogPosts(int pageNo, int pageSize, String sortBy, boolean ascending);

    public List<BlogPostDto> getBlogPostsByCategory(int categoryId);

    public List<BlogPostDto> getBlogPostsByUser(int userId);

    List<BlogPostDto> searchPosts(String keyword);
}
