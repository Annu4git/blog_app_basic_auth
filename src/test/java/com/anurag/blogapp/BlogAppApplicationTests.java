package com.anurag.blogapp;

import com.anurag.blogapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogAppApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testRepository() {
		String className = this.userRepository.getClass().getName();
		String packageName = this.userRepository.getClass().getPackageName();

		System.out.println("Class name : " + className);
		System.out.println("Package name : " + packageName);
	}

}
