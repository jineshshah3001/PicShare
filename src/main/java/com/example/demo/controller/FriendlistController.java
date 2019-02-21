package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Friends;
import com.example.demo.entity.User;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class FriendlistController {

	
	@Autowired
	private UserRepository userrepo;
	@Autowired
	private FriendRepository friendrepo;
	@RequestMapping(value="/viewfriends")
	public ModelAndView handlefriends(HttpServletRequest request)
	{
		ModelAndView mv =new ModelAndView();
		System.out.println("xtfcjgvhjbkjnkbhgchfxgdxgfvkhbjhvcjxhsxgjckvbj.vjcghjgvhb");
		String userId = (String)request.getSession().getAttribute("userId");
		User userObj = userrepo.findByUserid(userId);
		
		List<Friends> friends = new ArrayList<Friends>();
		List<User> abc = new ArrayList<User>();
		
		friends = friendrepo.findByUser(userObj);
		for(int i = 0; i < friends.size(); i++) {
            System.out.println(friends.get(i));
        }		
		for(int i=0;i<friends.size();i++)
		{
			Friends friend = friends.get(i);
			String friendId = friend.getFriendid();
			User us = userrepo.findByUserid(friendId);
			abc.add(us);
		}
		
		
		mv.addObject("abc",abc);
		mv.setViewName("viewfriends");
		return mv;
	}
}
