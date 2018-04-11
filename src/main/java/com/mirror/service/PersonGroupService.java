package com.mirror.service;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirror.dao.config.Constants;
import com.mirror.dao.models.Person;

@Service
public class PersonGroupService {
	
	@Autowired
	PersonService personService;
	
	public void createNew(String personGroupId, String userData)
	{
		try
		{
			HttpPut request = new HttpPut(new URIBuilder(Constants.getCreateNewPersonGroupUrl(personGroupId)).build());
			request.setEntity(new StringEntity("{ \"name\":\""+ personGroupId +"\",\"userData\":\""+userData+"\" }"));
			request.setHeader("Content-Type", "application/json");
			HttpRequestService.SendRequest(request);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public Person[] getAllPersons(String personGroupId)
	{
		try
		{
			HttpGet request = new HttpGet(new URIBuilder(Constants.getPersonResourceUrl(personGroupId)).build());
			ObjectMapper objectMapper =new ObjectMapper();
			Person[] personArray = objectMapper.readValue( HttpRequestService.SendRequest(request), Person[].class);
			return personArray;
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public void train(String personGroupId)
	{
		System.out.println("Training person group");
		try
		{
			HttpPost request = new HttpPost(new URIBuilder(Constants.getTrainPersonGroupUrl(personGroupId)).build());
			HttpRequestService.SendRequest(request);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("Person group trained");
	}
	
	public void removeAllPersonsInPersonGroup(String personGroupId) {
		Person[] persons = getAllPersons(personGroupId);
		for (int i = 0; i < persons.length; i++) {
			personService.deletePerson(persons[i].personId,personGroupId);
		}
	}
}
