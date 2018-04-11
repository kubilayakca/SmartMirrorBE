package com.mirror.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirror.dao.config.Constants;
import com.mirror.dao.models.Person;

@Service
public class PersonService {
	
	public Person getPerson(String personId,String personGroupId)
	{
		try
		{
			HttpGet request = new HttpGet(new URIBuilder(Constants.getPersonResourceUrl(personGroupId) +"/" +personId).build());
			
			ObjectMapper objectMapper = new ObjectMapper();
			Person person = objectMapper.readValue(HttpRequestService.SendRequest(request), Person.class);
			return person;
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public void deletePerson(String personId,String personGroupId)
	{
		try
		{
			HttpDelete request = new HttpDelete(new URIBuilder(Constants.getPersonResourceUrl(personGroupId) +"/" +personId).build());
			HttpRequestService.SendRequest(request);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public Person createPerson(String personName,String personData,String personGroupId)
	{
		try
		{
			HttpPost request = new HttpPost(new URIBuilder(Constants.getPersonResourceUrl(personGroupId)).build());
			request.setHeader("Content-Type", "application/json");
			request.setEntity(new StringEntity("{ \"name\":\""+personName+"\",\"userData\":\""+ personData +"\"}"));
			
			ObjectMapper objectMapper = new ObjectMapper();
			Person person = objectMapper.readValue(HttpRequestService.SendRequest(request), Person.class);
			return person;
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public void addPersonFace(String personId,File image,String personGroupId)
	{
		System.out.println("Uploading image...");
		try
		{
			
			//File file = ResourceUtils.getFile(this.getClass().getResource("/test.JPG"));
			Path fileLocation = Paths.get(image.getPath());
			
			HttpPost request = new HttpPost(new URIBuilder(Constants.getAddPersonFaceUrl(personGroupId, personId)).build());
			request.setEntity(new ByteArrayEntity(Files.readAllBytes(fileLocation)));
			request.setHeader("Content-Type", "application/octet-stream");
			
			System.out.println("Upload completed. " + HttpRequestService.SendRequest(request).toString());
			
			//ObjectMapper objectMapper =new ObjectMapper();
			//Face[] detectedFaceArray = objectMapper.readValue( HttpRequestService.SendRequest(request), Face[].class);
			//return detectedFaceArray;
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
	
}
