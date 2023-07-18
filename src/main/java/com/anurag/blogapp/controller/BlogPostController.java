package com.anurag.blogapp.controller;

import com.anurag.blogapp.Dto.ApiResponse;
import com.anurag.blogapp.Dto.BlogPostDto;
import com.anurag.blogapp.Dto.BlogPostResponse;
import com.anurag.blogapp.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog-post")
public class BlogPostController {

    @Autowired
    BlogPostService blogPostService;

    @PostMapping
    public ResponseEntity<BlogPostDto> createBlogPost(@RequestBody BlogPostDto blogPostDto,
                                                      @RequestParam int userId,
                                                      @RequestParam int categoryId) {
        blogPostDto = blogPostService.createBlogPost(blogPostDto, userId, categoryId);
        return new ResponseEntity<>(blogPostDto, HttpStatus.OK);
    }

    @PutMapping("/{blogPostId}")
    public ResponseEntity<BlogPostDto> updateBlogPost(@RequestBody BlogPostDto blogPostDto,
                                                      @PathVariable int blogPostId,
                                                      @RequestParam int categoryId) {
        blogPostDto = blogPostService.updateBlogPost(blogPostDto, blogPostId, categoryId);
        return new ResponseEntity<>(blogPostDto, HttpStatus.OK);
    }

    @DeleteMapping("{blogPostId}")
    public ResponseEntity<ApiResponse> deleteBlogPost(@PathVariable int blogPostId) {
        blogPostService.deleteBlogPost(blogPostId);
        return new ResponseEntity<>(new ApiResponse("Blog post deleted successfully", true),
                HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<BlogPostResponse> getAllBlogPost(
            @RequestParam(defaultValue = "10", required = false) int pageNo,
            @RequestParam(defaultValue = "0", required = false) int pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "true", required = false) boolean ascending,
            @RequestParam(defaultValue = "", required = false) String keyword) {

        BlogPostResponse blogPostResponse = blogPostService.getAllBlogPosts(pageNo,
                pageSize, sortBy, ascending);
        return new ResponseEntity<>(blogPostResponse, HttpStatus.OK);
    }

    @GetMapping("/{blogPostId}")
    public ResponseEntity<BlogPostDto> getBlogPost(@PathVariable int blogPostId) {
        return new ResponseEntity<>(blogPostService.getBlogPost(blogPostId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BlogPostDto>> searchPosts(@RequestParam String keyword) {
        return new ResponseEntity<>(blogPostService.searchPosts(keyword), HttpStatus.OK);
    }
}
