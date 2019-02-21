package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserFriendComposite implements Serializable  {
	private String friendid;
	 private User user;
}