package com.anurag.blogapp.repository;

import com.anurag.blogapp.entity.BlogPost;
import com.anurag.blogapp.entity.Category;
import com.anurag.blogapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {

    List<BlogPost> findByUser(User user);

    List<BlogPost> findByCategory(Category category);

    List<BlogPost> findByTitleContaining(String keyword);

}
