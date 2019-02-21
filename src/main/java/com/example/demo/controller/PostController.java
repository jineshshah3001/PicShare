package com.example.demo.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UploadToS3;



@Controller
public class PostController {

	@Autowired
	private PostRepository postrepository;
	
	@Autowired
	private CommentRepository commentrepository;
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	UploadToS3 s3;
	
	@GetMapping(value="/createpost")
	public ModelAndView renderpost()
	{
		return new ModelAndView("post");
	}
	
	@PostMapping(value="/base64Audio")
	public ModelAndView renderPage(HttpServletRequest request, @RequestParam(name="propic") MultipartFile image,@RequestParam("recording") String recording,@RequestParam("text") String text) throws Exception
	{
		ModelAndView mv = new ModelAndView();
		if(recording.isEmpty())throw new Exception("Recording is null");
		Decoder decoder = Base64.getDecoder();
		System.out.println(recording);
		byte [] decodedByte = decoder.decode(recording.split(",")[1]);
		FileOutputStream fos;
		try {
			fos= new FileOutputStream("MyAudionTemp.webm");
			fos.write(decodedByte);
			fos.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			
		}
		
	
		String acessKey = System.getenv("Access_key");
		String secret = System.getenv("secret");
		
		BasicAWSCredentials cred = new BasicAWSCredentials(acessKey,secret);
		
		
		
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
			// String  = UUID.randomUUID().toString();
			 String imgSrc ="http://" + "assignment2se" + ".s3.amazonaws.com/"+image.getOriginalFilename();
			
		Post postObj = new Post();
		
		String myId =  (String)request.getSession().getAttribute("userId");
		 String uuid = UUID.randomUUID().toString();

		String audiourl =s3.upload(uuid, new FileInputStream("MyAudionTemp.webm"));
		
		postObj.setUserid(myId);
		postObj.setAudiourl(audiourl);
		postObj.setText(text);
		postObj.setImguri(imgSrc);
		
		
		postrepository.save(postObj);
		mv.addObject("postObj",postObj);
		//mv.addObject("audiourl",audiourl);
		mv.setViewName("redirect:/savepost");
		return mv;
		
	}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mv.setViewName("successpage");
			return mv;
			
		}
}
	@GetMapping(value="/savepost")
	public ModelAndView post(HttpServletRequest request) throws Exception
	{
			ModelAndView mv = new ModelAndView();
			String myId = (String) request.getSession().getAttribute("userId");
			System.out.println(myId);
			User user = userrepository.findByUserid(myId);
			mv.addObject("u", user);
			List<Post> post = new ArrayList<Post>();
			post =  postrepository.findByUserid(myId);
			for(int i = 0; i < post.size(); i++) {
	            System.out.println(post.get(i).getImguri());
	        }
			mv.addObject("post", post);
		
			mv.setViewName("successful");
					return mv;
}
	@GetMapping(value="/play")
	public ModelAndView play(HttpServletRequest request,@RequestParam("postid") Long postid) throws Exception
	{
			ModelAndView mv = new ModelAndView();
			Post post = postrepository.findByPostid(postid);
			List<Comment> comment = commentrepository.findByPost(post); 
			mv.addObject("post",post);
			mv.addObject("comment",comment);
			
			mv.setViewName("showpost");
					return mv;
}
	
	@GetMapping(value="/play2")
	public ModelAndView play2(HttpServletRequest request,@RequestParam("postid") Long postid) throws Exception
	{
		System.out.println("heyyyy123");
			ModelAndView mv = new ModelAndView();
			Post post = postrepository.findByPostid(postid);
			mv.addObject("post",post);
			mv.setViewName("showpost");
					return mv;
}
	
	
}