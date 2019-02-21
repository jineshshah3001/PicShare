package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Friends;
import com.example.demo.entity.User;
import com.example.demo.entity.UserFriendComposite;

@Repository
public interface FriendRepository extends CrudRepository<Friends , UserFriendComposite> {
	List<Friends> findByUser(User user);
}
