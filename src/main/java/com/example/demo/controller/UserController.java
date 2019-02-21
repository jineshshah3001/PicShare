package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Friends;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository user;
	
	@Autowired
	private PostRepository repo;
	
	@GetMapping(value="/")
	public ModelAndView renderIndex() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
	
	@PostMapping(value="/redirect")
	public ModelAndView handleRedirect(
			@RequestParam(name="myId") String myId,
			@RequestParam(name="myName") String name,
			@RequestParam(name="myFriends") String friends,
			HttpServletRequest request) throws JSONException
	{
		System.out.println("in facebook redirect");
		ModelAndView mv = new ModelAndView();
		request.getSession().setAttribute("name", name);
		request.getSession().setAttribute("userId",myId);
		
		System.out.println("session attrs :"+ name + myId);
		
		String adminId = (String) request.getSession().getAttribute("userId");
		System.out.println(adminId);
			if(adminId.equals("1245576875577204"))
			{
				mv.setViewName("redirect:/adminPage");
				return mv;
			}
		boolean b = user.existsByUserid(myId);
		
		System.out.println("boolean b value "+b);
		if(b==true) {
			
			User u = user.findByUserid(myId) ;
			System.out.println(u.getDescription());
			System.out.println(u.getName());
			String userid =  (String)request.getSession().getAttribute("userId");
			mv.addObject("u",u);
			List<Post> post = new ArrayList<Post>();
			post =  repo.findByUserid(userid);
			for(int i=0;i<post.size();i++)
			{
				System.out.println(post.get(i));
			}
			mv.addObject("post", post);
			mv.setViewName("successful");
			
			return mv;
			
					}
		else {
			
			User userObj =new User();
			System.out.println("before setting id " +myId);
			userObj.setUserid(myId);
			System.out.println("after setting" +userObj.toString());
			userObj.setName(name);
			
			List<Friends> frnds = new ArrayList<Friends>();
			JSONArray jsonarray = new JSONArray(friends);
			
			
			int count=1;
			for(int i=0 ; i<jsonarray.length();i++) {
				
				Friends object = new Friends();
				
				
				System.out.println(count);
				 JSONObject explrObject = jsonarray.getJSONObject(i);
				 String id =explrObject.getString("id");
				 object.setFriendid(id);
				 object.setUser(userObj);
				 frnds.add(object);
				 count++;
				  
				 
			}
			userObj.setFriends(frnds);
			System.out.println("Saving user obj"+userObj.toString());
			user.save(userObj);
			System.out.println("User obj saved"+userObj.toString());
			mv.setViewName("create_profile");
			return mv;
			
			}
	}
	
	
	
}