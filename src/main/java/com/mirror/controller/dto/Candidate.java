package com.mirror.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Candidate {
	public String personId; 
	public Double confidence; 
}
