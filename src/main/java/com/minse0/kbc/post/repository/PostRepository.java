package com.minse0.kbc.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.minse0.kbc.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	public 	Page<Post> findAll(Pageable pageable);
	public  List<Post> findAll();
}
