package com.mirror.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentifyResponse {
	public String faceId;
	public Candidate[] candidates;
}
