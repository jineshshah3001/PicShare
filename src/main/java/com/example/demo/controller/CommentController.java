package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class CommentController {
	

	@Autowired
	private PostRepository postrepo;
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private CommentRepository commentrepo;

	@PostMapping(value="/savecomment")
	public ModelAndView savecomment(@RequestParam (name="comment") String comment,
			HttpServletRequest request,@RequestParam(name="postid") long postid)
			//RedirectAttributes redirectAttributes)
	{
		ModelAndView mv = new ModelAndView();
		Comment c = new Comment();
		
		String userId = (String) request.getSession().getAttribute("userId");
		User user = userrepo.findByUserid(userId);
		Post post = postrepo.findByPostid(postid);
		
		String name = (String) request.getSession().getAttribute("name");
		
		c.setName(name);
		c.setComment(comment);
		c.setPost(post);
		c.setUser(user);
		commentrepo.save(c);
		mv.addObject("c",c);
		mv.addObject("post", post);
		List<Comment> comments = commentrepo.findByPost(post);
		mv.addObject("comment", comments);
		mv.addObject("comment",comments);
		//redirectAttributes.addFlashAttribute(postid);
		mv.setViewName("showpost");
		return mv;
	}
}
