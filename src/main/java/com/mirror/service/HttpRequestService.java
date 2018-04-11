package com.mirror.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.mirror.dao.config.Constants;

@Service
public class HttpRequestService {
	
	public static String SendRequest(HttpRequestBase request)
	{
		request.setHeader("Ocp-Apim-Subscription-Key", Constants.getSubsriptionKey());
		
		try
		{
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String responseString = EntityUtils.toString(entity);
				System.out.println(responseString);
				return responseString;
			}
		}catch(
		Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		return null;
	}
}
