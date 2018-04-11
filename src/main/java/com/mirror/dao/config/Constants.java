package com.mirror.dao.config;

public class Constants {
	private static final String SUBSRIPTION_KEY = "1e68c49a62b94a459eefdc7fae506d04";
	private static final String PERSON_GROUP_ID = "acn-employee";
	
	private static final String BASE_URL = "https://southcentralus.api.cognitive.microsoft.com/face/v1.0/";	
	private static final String DETECT_URL =  BASE_URL + "detect";
	private static final String IDENTIFY_URL =  BASE_URL + "identify";
	private static final String PERSON_RESOURCE_URL =  BASE_URL + "/persongroups/";
	private static final String ADD_PERSON_FACE_URL =  BASE_URL + "persongroups/";
	
	private static final String TRAIN_PERSON_GROUP_URL = BASE_URL + "persongroups/";
	private static final String CREATE_NEW_PERSON_GROUP_URL = BASE_URL + "persongroups/";

	public static String getDetectUrl() {
		return DETECT_URL;
	}

	public static String getSubsriptionKey() {
		return SUBSRIPTION_KEY;
	}

	public static String getIdentifyUrl() {
		return IDENTIFY_URL;
	}

	public static String getPersonResourceUrl(String personGroupId) {
		return PERSON_RESOURCE_URL + personGroupId + "/persons";
	}

	public static String getAddPersonFaceUrl(String personGroupId,String personId) {
		return ADD_PERSON_FACE_URL  + personGroupId + "/persons/"+ personId + "/persistedFaces";
	}

	public static String getCreateNewPersonGroupUrl(String personGroupId) {
		return CREATE_NEW_PERSON_GROUP_URL + personGroupId;
	}

	public static String getPersonGroupId() {
		return PERSON_GROUP_ID;
	}

	public static String getTrainPersonGroupUrl(String personGroupId) {
		return TRAIN_PERSON_GROUP_URL + personGroupId + "/train";
	}
	
}
