package com.mirror.service;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirror.controller.dto.IdentifyResponse;
import com.mirror.dao.config.Constants;
import com.mirror.dao.models.Face;

@Service
public class FaceService {
	
	/**
	 * Detects the faces in given image and returns faceIds
	 */
	public Face[] detectFace(byte[] imageByteArray)
	{
		System.out.println("Detecting faces...");
		try
		{
			HttpPost request = new HttpPost(new URIBuilder(Constants.getDetectUrl()).build());
			request.setEntity(new ByteArrayEntity(imageByteArray));
			request.setHeader("Content-Type", "application/octet-stream");
			 
			ObjectMapper objectMapper =new ObjectMapper();
			Face[] detectedFaceArray = objectMapper.readValue( HttpRequestService.SendRequest(request), Face[].class);
			return detectedFaceArray;
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public IdentifyResponse[] identifyFace(Face face,String personGroupId)
	{
		try
		{
			HttpPost request = new HttpPost(new URIBuilder(Constants.getIdentifyUrl()).build());
			request.setHeader("Content-Type", "application/json");
			request.setEntity(new StringEntity("{ \"personGroupId\":\""+personGroupId+"\",\"maxNumOfCandidatesReturned\":1,\"confidenceThreshold\":0.5, \"faceIds\":[\""+face.faceId+"\"],}"));
			ObjectMapper objectMapper = new ObjectMapper();
			IdentifyResponse[] identifyResponseArray = objectMapper.readValue( HttpRequestService.SendRequest(request), IdentifyResponse[].class);
			return identifyResponseArray;
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

}
