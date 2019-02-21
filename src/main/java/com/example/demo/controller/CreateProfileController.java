package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.entity.Post;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.repository.CreateProfileRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class CreateProfileController {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PostRepository postrepo;

	@GetMapping("/createProfile")
	public ModelAndView renderPage() {
		ModelAndView mv = new ModelAndView();
		
		
		mv.setViewName("create_profile");
		return mv;
	}
		
		@PostMapping(value="/save")
		public ModelAndView saveProfile(
				
				 @RequestParam(name="description" , required=true) String description,
				 @RequestParam(name="propic") MultipartFile image,
				 HttpServletRequest request) {
		
			ModelAndView mv = new ModelAndView();
			String acessKey = System.getenv("Access_key");
			String secret = System.getenv("secret");
			ModelAndView profilePage = new ModelAndView();
			BasicAWSCredentials cred = new BasicAWSCredentials(acessKey,secret);
			
			String imgSrc;
			
			AmazonS3 s3client = AmazonS3ClientBuilder
					.standard()
					.withCredentials(new AWSStaticCredentialsProvider(cred) )
					.withRegion(Regions.US_EAST_2)
					.build();
			try {
				
				PutObjectRequest putReq = new PutObjectRequest("assignment2se",
						image.getOriginalFilename(), image.getInputStream(), new ObjectMetadata())
						.withCannedAcl(CannedAccessControlList.PublicRead);
					
				s3client.putObject(putReq);
				// String uuid = UUID.randomUUID().toString();
				imgSrc ="http://" + "assignment2se" + ".s3.amazonaws.com/"+image.getOriginalFilename();
				profilePage.addObject("imgSrc", imgSrc);
				
			//	User user = new User();
				
				
				String myId =  (String)request.getSession().getAttribute("userId");
				//String name =  (String)request.getSession().getAttribute("name");
				//System.out.println("******************"+name);
				System.out.println("session attrs in create profile:"+  myId);
				
				User user = repo.findByUserid(myId);
				
				user.setDescription(description);
				user.setImgurl(imgSrc);
				
				repo.save(user);
				mv.addObject("u",user);
				mv.setViewName("successful");
				return mv;
				
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				profilePage.setViewName("error");
				return profilePage;
			}
			
			
	}
		
		@GetMapping(value="/editprofile")
		public ModelAndView editprofile(HttpServletRequest request)
		{
			ModelAndView mv = new ModelAndView();
			String userId =  (String)request.getSession().getAttribute("userId"); 
			User edit = repo.findByUserid(userId);
			mv.addObject("edit", edit);
			mv.setViewName("edit_profile");
			return mv;
			
		}
		
@GetMapping(value="/test2/{userid}")
public ModelAndView viewprofile(@PathVariable("userid") String userid)
{
	System.out.println("hryy");
	System.out.println(userid);
	ModelAndView mv = new ModelAndView();
	User u  =  repo.findByUserid(userid);
	List<Post> post = new ArrayList<Post>();
	post = postrepo.findByUserid(userid);
	mv.addObject("u", u);
	mv.addObject("post", post);
	
	mv.setViewName("friendprofile");
	return mv;
			}

@PostMapping(value="/saveedit")
public ModelAndView vieweditedprofile( @RequestParam(name="description") String description,
		 @RequestParam(name="propic") MultipartFile image,HttpServletRequest request)
{
	
	ModelAndView mv = new ModelAndView();
	String userId =  (String)request.getSession().getAttribute("userId"); 
	User u = repo.findByUserid(userId);
	u.setDescription(description);
	
	String acessKey = System.getenv("Access_key");
	String secret = System.getenv("secret");
	ModelAndView profilePage = new ModelAndView();
	BasicAWSCredentials cred = new BasicAWSCredentials(acessKey,secret);
	
	String imgSrc;
	
	AmazonS3 s3client = AmazonS3ClientBuilder
			.standard()
			.withCredentials(new AWSStaticCredentialsProvider(cred) )
			.withRegion(Regions.US_EAST_2)
			.build();
	try {
		
		PutObjectRequest putReq = new PutObjectRequest("assignment2se",
				image.getOriginalFilename(), image.getInputStream(), new ObjectMetadata())
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
		s3client.putObject(putReq);
		// String uuid = UUID.randomUUID().toString();
		imgSrc ="http://" + "assignment2se" + ".s3.amazonaws.com/"+image.getOriginalFilename();
		u.setImgurl(imgSrc);
		List<Post> post =new ArrayList<Post>();
		post= postrepo.findByUserid(userId);
		mv.addObject("u", u);
		mv.addObject("post", post);
		
	mv.setViewName("successful");
	return mv;
	
}
	catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		mv.setViewName("error");
		return mv;
	}

	
	
}

}
