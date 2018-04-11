package com.mirror.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import com.mirror.controller.dto.IdentifyResponse;
import com.mirror.dao.models.Face;
import com.mirror.dao.models.Person;

@EnableScheduling
@Service
public class SmartMirrorService {
	
	@Autowired
	FaceService faceService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;
	
	private final String personGroupId = "acn-employee";

	@Scheduled(fixedDelay=2000)
	public void startLoop() throws FailedToRunRaspistillException {
		byte[] capturedImage = captureImage();
		processImage(capturedImage);
	}

	private void processImage(byte[] capturedImage) {

		Face[] detectedFaces = detectFaces(capturedImage);
		
		if(detectedFaces.length > 0 && detectedFaces[0] != null ) {
			System.out.println("Face detected.");
			IdentifyResponse[] identifyResponse = faceService.identifyFace(detectedFaces[0],personGroupId);
			
			if(identifyResponse.length > 0 &&  identifyResponse[0] != null && identifyResponse[0].candidates.length > 0 && identifyResponse[0].candidates[0] != null) {
				Person identifiedPerson = personService.getPerson(identifyResponse[0].candidates[0].personId, personGroupId);
				System.out.println("Identified Person: " + identifiedPerson.name);
				simpMessagingTemplate.convertAndSend("/topic/greetings",identifiedPerson.name);
			}
			else {
				System.out.println("Person not recognized.");
				simpMessagingTemplate.convertAndSend("/topic/greetings","");
			}
		}
		else {
			simpMessagingTemplate.convertAndSend("/topic/greetings","");
			System.out.println("Face not detected.");
		}

	}

	public byte[] captureImage() throws FailedToRunRaspistillException {
		
		// Create a Camera that saves images to the Pi's Pictures directory.
		RPiCamera piCamera = new RPiCamera("/home/pi/Pictures");
		piCamera.turnOffPreview();
		piCamera.setQuality(60);
		piCamera.setTimeout(100);


		BufferedImage image;
		try {
			image = piCamera.takeBufferedStill();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			return baos.toByteArray();
			
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		} catch (InterruptedException e) {
			System.out.println(e.getStackTrace());
		}
		return null;
		
	}

	private Face[] detectFaces(byte[] imageByteArray) {
		return faceService.detectFace(imageByteArray);
	}
}
