package com.Santosh.Cyborg.HttpStatus;

import java.util.ArrayList;


public class ImageChecker {

	//ArrayList to store all the Broken images. 
    private ArrayList<Object> brokenImages = null;
	
	
    /** This method checks all the image Urls provided as input and check if any of the image
     * does not return response status as 200
     * @param imageURLs - ArrayList of image URLs the status of which needs to checked.
     * @param thread_Count - Integer argument to provided to Executor service to create
     * multiple threads.
     * @return returns are ArrayList of broken images.
     */
    public ArrayList<Object> getBrokenImages (ArrayList<Object> imageURLs, int thread_Count) {
		
		  brokenImages = new HttpStatusChecker().getURLsForCode(imageURLs, 200, thread_Count);
		  
		  return brokenImages;
		
	}
	
}
