package com.mirror.dao.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
	public String personId;
	public String name;
	public String userData;
	public int maxNumOfCandidatesReturned;
	public Double confidenceThreshold;
}
