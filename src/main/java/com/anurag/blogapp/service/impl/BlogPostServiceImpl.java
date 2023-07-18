package com.anurag.blogapp.service.impl;

import com.anurag.blogapp.Dto.BlogPostResponse;
import com.anurag.blogapp.Dto.CategoryDto;
import com.anurag.blogapp.Dto.BlogPostDto;
import com.anurag.blogapp.Dto.UserDto;
import com.anurag.blogapp.entity.BlogPost;
import com.anurag.blogapp.entity.Category;
import com.anurag.blogapp.entity.User;
import com.anurag.blogapp.exception.ResourceNotFoundException;
import com.anurag.blogapp.repository.BlogPostRepository;
import com.anurag.blogapp.service.CategoryService;
import com.anurag.blogapp.service.BlogPostService;
import com.anurag.blogapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    @Autowired
    BlogPostRepository blogPostRepository;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public BlogPostDto createBlogPost(BlogPostDto blogPostDto, int userId, int categoryId) {

        UserDto userDto = userService.getUserById(userId);

        CategoryDto categoryDto = categoryService.getCategory(categoryId);

        BlogPost blogPost = modelMapper.map(blogPostDto, BlogPost.class);
        blogPost.setUser(modelMapper.map(userDto, User.class));
        blogPost.setCategory(modelMapper.map(categoryDto, Category.class));
        blogPost.setCreatedDate(new Date());
        blogPost.setUpdatedDate(new Date());

        return modelMapper.map(blogPostRepository.save(blogPost), BlogPostDto.class);
    }

    @Override
    public BlogPostDto updateBlogPost(BlogPostDto blogPostDto, int postId, int categoryId) {
        BlogPost blogPost = blogPostRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "Post id", postId));

        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        blogPost.setCategory(modelMapper.map(categoryDto, Category.class));

        // we are not updating user in update blog post, assuming that user cant be updated
        blogPost.setContent(blogPostDto.getContent());
        blogPost.setUpdatedDate(new Date());

        return modelMapper.map(blogPostRepository.save(blogPost), BlogPostDto.class);
    }

    @Override
    public void deleteBlogPost(int postId) {
        BlogPost blogPost = blogPostRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "Post id", postId));
        blogPostRepository.delete(blogPost);
    }

    @Override
    public BlogPostDto getBlogPost(int postId) {
        BlogPost blogPost = blogPostRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog Post", "Post id", postId));
        return modelMapper.map(blogPost, BlogPostDto.class);
    }

    @Override
    public BlogPostResponse getAllBlogPosts(int pageNo, int pageSize, String sortBy, boolean ascending) {
        Sort sort = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize).withSort(sort);
        Page<BlogPost> blogPostPage = blogPostRepository.findAll(pageable);
        List<BlogPost> blogPostList = blogPostPage.getContent();
        List<BlogPostDto> content = blogPostList.stream()
                .map(blogPost -> modelMapper.map(blogPost, BlogPostDto.class))
                .collect(Collectors.toList());
        BlogPostResponse blogPostResponse = new BlogPostResponse();
        blogPostResponse.setContent(content);
        blogPostResponse.setPageNo(blogPostPage.getNumber());
        blogPostResponse.setPageSize(blogPostPage.getSize());
        blogPostResponse.setTotalElements(blogPostPage.getTotalElements());
        blogPostResponse.setTotalPages(blogPostPage.getTotalPages());
        blogPostResponse.setLastPage(blogPostPage.isLast());
        return blogPostResponse;
    }

    @Override
    public List<BlogPostDto> getBlogPostsByCategory(int categoryId) {
        Category category = modelMapper.map(categoryService.getCategory(categoryId), Category.class);

        return blogPostRepository.findByCategory(category).stream()
                .map(blogPost -> modelMapper.map(blogPost, BlogPostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BlogPostDto> getBlogPostsByUser(int userId) {
        User user = modelMapper.map(userService.getUserById(userId), User.class);

        return blogPostRepository.findByUser(user).stream()
                .map(blogPost -> modelMapper.map(blogPost, BlogPostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<BlogPostDto> searchPosts(String keyword) {
        return blogPostRepository.findByTitleContaining(keyword).stream()
                .map(blogPost -> modelMapper.map(blogPost, BlogPostDto.class)).collect(Collectors.toList());
    }
}
