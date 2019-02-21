package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Post {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long postid;
	public String imguri;
	
	
	private String text;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	String audiourl;
	String userid;
	
	public Long getPostid() {
		return postid;
	}
	public void setPostid(Long postid) {
		this.postid = postid;
	}
	public String getImguri() {
		return imguri;
	}
	public void setImguri(String imguri) {
		this.imguri = imguri;
	}
	
	public String getAudiourl() {
		return audiourl;
	}
	public void setAudiourl(String audiourl) {
		this.audiourl = audiourl;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	
	
	
	public Post(Long postid, String imguri, String text, String audiourl, String userid, List<Comment> comment) {
		super();
		this.postid = postid;
		this.imguri = imguri;
		this.text = text;
		this.audiourl = audiourl;
		this.userid = userid;
		this.comment = comment;
	}
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}
	@OneToMany(targetEntity = Comment.class, mappedBy = "post",cascade = CascadeType.ALL)
    List<Comment> comment;
	public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	

}
