package com.mirror.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.mirror.dao.models.Person;

@Service
public class BatchUploadService {
	
	@Autowired
	FaceService faceService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	PersonGroupService personGroupService;
	
	public void startBatchUpload(String personGroupId) throws FileNotFoundException {
		createPersonsAndUploadFaces(personGroupId);
		personGroupService.train(personGroupId);
	}
	
	private void createPersonsAndUploadFaces(String personGroupId) throws FileNotFoundException {
		
		System.out.println("BATCH UPLOAD STARTED");
		
		File file = ResourceUtils.getFile(this.getClass().getResource("/img"));
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		
		System.out.println(Arrays.toString(directories));
		
		for (int i = 0; i < directories.length; i++) {
			Person person = createPerson(directories[i],personGroupId);
			File personFolder = ResourceUtils.getFile(this.getClass().getResource("/img/" + directories[i]));
			File[] images = personFolder.listFiles();
			
			for (int j = 0; j < images.length; j++) {
				uploadFace(images[j],person,personGroupId);
			}
		}
		
		System.out.println("BATCH UPLOAD COMPLETED");
	}
	
	private Person createPerson(String unsplittedFolderName,String personGroupId) {
		String[] splittedText = unsplittedFolderName.split("-");
		String personName = splittedText[0];
		String personData = splittedText[1];
		
		System.out.println("Creating person: " + personName);
		Person person = personService.createPerson(personName, personData, personGroupId);
		System.out.println("Person Created: " + personName + " Person Id: " + person.personId);
		return person;
	}
	
	private void uploadFace(File image,Person person,String personGroupId) {
		System.out.println("Uploading face for person: " + person.name);
		personService.addPersonFace(person.personId, image, personGroupId);
		System.out.println("Face uploaded ");
	}

}
