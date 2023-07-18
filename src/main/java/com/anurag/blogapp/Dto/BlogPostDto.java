package com.anurag.blogapp.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class BlogPostDto {

    private int postId;

    private String title;

    private String content;

    private String image;

    private Date createdDate;

    private Date updatedDate;

    private CategoryDto category;

    private UserDto user;
}
