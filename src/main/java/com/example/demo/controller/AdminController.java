package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

@Controller

public class AdminController {
	
	@Autowired
	private UserRepository user;
	
	@Autowired
	private PostRepository postrepo;
	
	@GetMapping(value="/adminPage")
	public ModelAndView renderadminPage()
	{
		ModelAndView mv = new ModelAndView();
		List<User> allUsers = new ArrayList<User>();
		allUsers =  user.findAll();
		
		mv.addObject("allUsers", allUsers);
		mv.setViewName("Adminpage");
		return mv;
	}
	
	@GetMapping(value="/test/{userid}")
	public ModelAndView showAllPost(@PathVariable("userid") String userid ) {
		ModelAndView mv = new ModelAndView();
		List<Post> allPost = new ArrayList<Post>();
		allPost = postrepo.findAllByUserid(userid);
		System.out.println(userid);
		for(int i=0;i<allPost.size();i++)
		{
			System.out.println("Post" +i);
		}
		mv.addObject("allPost",allPost);
		mv.setViewName("showAllPost");
		return mv;
	}
	
	@GetMapping(value="/test1/{postid}")
	public ModelAndView deletePost(@PathVariable("postid") Long postid ) {
		ModelAndView mv = new ModelAndView();
		Post postt = postrepo.findByPostid(postid);
		String hey = postt.getUserid();
		System.out.println(hey);
		postrepo.delete(postt);
		List<Post> post = postrepo.findAllByUserid(hey);
		mv.addObject("allPost", post);
		mv.setViewName("showAllPost");
		return mv;
	}
}
