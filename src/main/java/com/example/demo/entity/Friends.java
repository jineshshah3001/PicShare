package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@IdClass(UserFriendComposite.class)
public class Friends {
	
@Id
@ManyToOne
@JoinColumn(name = "userid")
private User user;
@Id	
private String friendid;


public Friends() {
	super();
	// TODO Auto-generated constructor stub
}
public Friends(String friendid, User user) {
	super();
	this.friendid = friendid;
	this.user = user;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}
public String getFriendid() {
	return friendid;
}
public void setFriendid(String friendid) {
	this.friendid = friendid;
}

@Override
public String toString() {
	return "Friend [userObj=" + user + ", friendId=" + friendid + "]";
}
 
	
}