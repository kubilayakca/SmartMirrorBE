package com.mirror.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mirror.dao.models.Person;
import com.mirror.service.BatchUploadService;
import com.mirror.service.PersonGroupService;
import com.mirror.service.SmartMirrorService;

import lombok.extern.slf4j.Slf4j;

/**
 * Accepts HTTP web traffic for real time bill calculation operations.
 */
@Slf4j
@RestController
@RequestMapping("face")
public class FaceRecognitionController {
	
	@Autowired
	SmartMirrorService smartMirrorService;
	
	@Autowired
	PersonGroupService personGroupService;
	
	@Autowired
	BatchUploadService batchUploadService;
	
	private final String personGroupId = "acn-employee";
		
	@GetMapping("/startBatchUpload")
	public void startBatchUpload() throws FileNotFoundException {
		log.info("Request for start batch upload received.");
		batchUploadService.startBatchUpload(personGroupId);
	}
	
	@GetMapping("/getAllPersons")
	public Person[] startBatchUpload(@RequestParam(value = "personGroupId", required = true) String personGroupId) {
		log.info("Request for getAllPersons received.");
		return personGroupService.getAllPersons(personGroupId);
	}
	
	@GetMapping("/train")
	public void startTraining(@RequestParam(value = "personGroupId", required = true) String personGroupId) {
		log.info("Request for train received.");
		personGroupService.train(personGroupId);
	}
}
