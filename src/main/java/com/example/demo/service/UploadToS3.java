package com.example.demo.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.entity.User;

@Service
public class UploadToS3 {

		public String upload(String name,InputStream fs) {
			
			String acessKey = System.getenv("Access_key");
			String secret = System.getenv("secret");
			
			BasicAWSCredentials cred = new BasicAWSCredentials(acessKey,secret);
			
			String audioSrc;
			
			AmazonS3 s3client = AmazonS3ClientBuilder
					.standard()
					.withCredentials(new AWSStaticCredentialsProvider(cred) )
					.withRegion(Regions.US_EAST_2)
					.build();
			
				
				PutObjectRequest putReq = new PutObjectRequest("assignment2se",
						name, fs, new ObjectMetadata())
						.withCannedAcl(CannedAccessControlList.PublicRead);
					
				s3client.putObject(putReq);
				
				audioSrc ="http://" + "assignment2se" + ".s3.amazonaws.com/"+name;
				return audioSrc;
		}
		
		

			public String uploadphoto(String name,InputStream fs) {
				
				String acessKey = System.getenv("Access_key");
				String secret = System.getenv("secret");
				
				BasicAWSCredentials cred = new BasicAWSCredentials(acessKey,secret);
				
				String photoSrc;
				
				AmazonS3 s3client = AmazonS3ClientBuilder
						.standard()
						.withCredentials(new AWSStaticCredentialsProvider(cred) )
						.withRegion(Regions.US_EAST_2)
						.build();
				
					
					PutObjectRequest putReq = new PutObjectRequest("assignment2se",
							name, fs, new ObjectMetadata())
							.withCannedAcl(CannedAccessControlList.PublicRead);
						
					s3client.putObject(putReq);
					
					photoSrc ="http://" + "assignment2se" + ".s3.amazonaws.com/"+name;
					return photoSrc;
			}
		

}
