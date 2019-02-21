package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Post;

@Repository
public interface PostRepository extends CrudRepository<Post,String>{
	List<Post> findByUserid(String userId);
	Post findByPostid(Long postid);
	void deleteByPostid(Long postid);
	List<Post> findAllByUserid(String userId);
}
