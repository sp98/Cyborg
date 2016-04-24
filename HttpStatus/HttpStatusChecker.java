package com.Santosh.Cyborg.HttpStatus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class HttpStatusChecker {

	/* Initializing Global Variables */
	int actualStatuCode = 0;
	int startRange = 0;
	int endRange= 0;
	String [] statusCodes;
	int givenCode = 0;
	
	
	/*Initializing data Collectors */
	HashMap<Object, Object> urlStatusCodes = null;
	ArrayList<Object> urls = null;
	
	
	/** Used the get the HTTP Status Code for a single URL
	 * @param url - the URL for which response needs to be found.
	 * @return returns the status code of the URL. Returns -1 in case of any exception.
	 */
	public int getHttpStatusCode(URL url){
		
		int statusCode = 0;       //initializing the status code to 0
		
		try {
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.connect();
			statusCode =  connection.getResponseCode();
 		    connection.disconnect();
 		    return statusCode;
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return -1;
		}		
		
	}
	
	
	
	
	/**Used the get the HTTPs Status Code for a single URL
	 * @param url - the URL for which response needs to be found.
	 * @return returns the status code of the single URL
	 */
	public int getHttpsStatusCode(URL url){
		
        int statusCode = 0;       //initializing the status code to 0
		
		try {
			HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
			connection.connect();
			statusCode =  connection.getResponseCode();
 		    connection.disconnect();
 		    return statusCode;
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}
	
	
	/** Used to get the HTTP status of code a list of URLs passed as parameters
	 * @param urlList - An array list of URLs for which status code is required
	 * @param thread_Count - for fetching the status code of multiple URLs parallelly
	 * @return a HashMap containing URL-status code as Key-value pairs.
	 */
	public HashMap<Object, Object> getHttpStatusCodes(ArrayList<URL> urlList, int thread_Count){
		
		if(urlList.size()==0){
			return null;
		}
		
		urlStatusCodes = new HashMap<Object, Object>();   //initialize the array list  to store the status code for each URL
		actualStatuCode = 0;
		
		final CountDownLatch latch = new CountDownLatch(urlList.size());
		ExecutorService taskExecutor = Executors.newFixedThreadPool(thread_Count);
		
		for(final URL url : urlList){
			
			taskExecutor.execute(new Runnable(){

				public void run() {
					
					
					// TODO Auto-generated method stub
					try {
						
						HttpURLConnection connection = (HttpURLConnection)url.openConnection();
						connection.connect();
						actualStatuCode =  connection.getResponseCode();
			 		    connection.disconnect();
			 		    urlStatusCodes.put(url, actualStatuCode);
					} 
					
					catch (IOException e) {
						// TODO Auto-generated catch block
     					e.printStackTrace();							
					 }	
					
					latch.countDown();				
				}			
				
			});		
		}
		
		try {
	        latch.await();  // The main Thread will wait until the value of 'count' is 0
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
		taskExecutor.shutdown();  //Shuts down the ExecutorService, that is, all the Threads created by the executor service will be killed.
		
		return urlStatusCodes;
		
	}

	
	/** Used to get the HTTPs status of code a list of URLs passed as parameters
	 * @param urlList - An array list of URLs for which status code is required
	 * @param thread_Count - for fetching the status code of multiple URLs parallelly
	 * @return a HashMap containing URL-status code as Key-value pairs.
	 */
	public HashMap getHttpsStatusCodes(ArrayList<URL> urlList, int thread_Count){
		
		if(urlList.size()==0){
			return null;
		}
		
		urlStatusCodes = new HashMap<Object, Object>();   //initialize the array list  to store the status code for each URL
		actualStatuCode = 0;
		
		final CountDownLatch latch = new CountDownLatch(urlList.size());
		ExecutorService taskExecutor = Executors.newFixedThreadPool(thread_Count);
		
		for(final URL url : urlList){
			
			taskExecutor.execute(new Runnable(){

				public void run() {
					
					
					// TODO Auto-generated method stub
					try {
						
						HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
						connection.connect();
						actualStatuCode =  connection.getResponseCode();
			 		    connection.disconnect();
			 		    urlStatusCodes.put(url, actualStatuCode);
					} 
					
					catch (IOException e) {
						// TODO Auto-generated catch block
     					e.printStackTrace();							
					 }	
					
					latch.countDown();				
				}			
				
			});		
		}
		
		try {
	        latch.await();  // The main Thread will wait until the value of 'count' is 0
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
		taskExecutor.shutdown();  //Shuts down the ExecutorService, that is, all the Threads created by the executor service will be killed.
		
		return urlStatusCodes;
		
	}
	
	
	/** Used to get the HTTP status code, based on the String of Codes provided by the user, for a list of URLs passed as parameters
	 * @param urlList - An array list of URLs for which status code is required
	 * @param codes - String array of codes 
	 * @param thread_Count - for fetching the status code of multiple URLs parallelly
	 * @return a HashMap containing URL-status code as Key-value pairs.
	 */
	public HashMap<Object, Object> getHttpStatusCodes(ArrayList<URL> urlList, String [] codes, int thread_Count){
		
		
		/*check if the url List is empty. Return null if it is*/
		if(urlList.size()==0){
			return null;
		}
		
		statusCodes = codes;
		urlStatusCodes = new HashMap<Object, Object>();   //initialize the array list  to store the status code for each URL
		actualStatuCode = 0;
		
		final CountDownLatch latch = new CountDownLatch(urlList.size());
		ExecutorService taskExecutor = Executors.newFixedThreadPool(thread_Count);
		
		for(final URL url : urlList){
			
			taskExecutor.execute(new Runnable(){

				public void run() {
					
					
					// TODO Auto-generated method stub
					try {
						
						HttpURLConnection connection = (HttpURLConnection)url.openConnection();
						connection.connect();
						actualStatuCode =  connection.getResponseCode();
			 		    connection.disconnect();
			 		    
			 		    for(String code: statusCodes){
			 		    	if(actualStatuCode == Integer.parseInt(code)){
			 		    		urlStatusCodes.put(url, actualStatuCode);
			 		    	}
			 		    }
					} 
					
					catch (IOException e) {
						// TODO Auto-generated catch block
     					e.printStackTrace();							
					 }	
					
					latch.countDown();				
				}			
				
			});		
		}
		
		try {
	        latch.await();  // The main Thread will wait until the value of 'count' is 0
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
		taskExecutor.shutdown();  //Shuts down the ExecutorService, that is, all the Threads created by the executor service will be killed.
		
		return urlStatusCodes;
		
	}
	
	
	/** Used to get the HTTP status code based on the range provided by the user for a list of URLs passed as parameters
	 * @param urlList - An array list of URLs for which status code is required
	 * @param range - Contains a comma separated String that will store the start and end range.
	 * @param thread_Count - for fetching the status code of multiple URLs parallelly
	 * @return a HashMap containing URL-status code as Key-value pairs.
	 */
	public HashMap getHttpStatusCodes(ArrayList<URL> urlList, String range, int thread_Count){
		
		/*check if the url List is empty. Return null if it is*/
		if(urlList.size()==0){
			return null;
		}
		
		/*get the status code range */
		if(range.split(",").length!= 2){
			return null;
		}
		
		try{
			startRange =  Integer.parseInt(range.split(",")[0].trim());
			endRange = Integer.parseInt(range.split(",")[1].trim());
		}
		catch(NumberFormatException e){
			e.printStackTrace();
			//print some messagd over here
		}	
				
		
		urlStatusCodes = new HashMap<Object, Object>();   //initialize the array list  to store the status code for each URL
		actualStatuCode = 0;
		
		final CountDownLatch latch = new CountDownLatch(urlList.size());
		ExecutorService taskExecutor = Executors.newFixedThreadPool(thread_Count);
		
		for(final URL url : urlList){
			
			taskExecutor.execute(new Runnable(){

				public void run() {
					
					
					// TODO Auto-generated method stub
					try {
						
						HttpURLConnection connection = (HttpURLConnection)url.openConnection();
						connection.connect();
						actualStatuCode =  connection.getResponseCode();
			 		    connection.disconnect();
			 		    if(actualStatuCode>=startRange && actualStatuCode<=endRange){
			 		    	urlStatusCodes.put(url, actualStatuCode);
			 		    }	 		    
					} 
					
					catch (IOException e) {
						// TODO Auto-generated catch block
     					e.printStackTrace();							
					 }	
					
					latch.countDown();				
				}			
				
			});		
		}
		
		try {
	        latch.await();  // The main Thread will wait until the value of 'count' is 0
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
		taskExecutor.shutdown();  //Shuts down the ExecutorService, that is, all the Threads created by the executor service will be killed.
		
		return urlStatusCodes;
		
	}
	
	

/**
 * @param urlList
 * @param code
 * @param thread_Count
 * @return
 */
public ArrayList<Object> getURLsForCode(ArrayList<Object> urlList, int code, int thread_Count){
		
		/*check if the url List is empty. Return null if it is*/
		if(urlList.size()==0){
			return null;
		}
		
			
		givenCode = code;
		urlStatusCodes = new HashMap<Object, Object>();   //initialize the array list  to store the status code for each URL
		actualStatuCode = 0;
		urls = new ArrayList<Object>();
		
		final CountDownLatch latch = new CountDownLatch(urlList.size());
		ExecutorService taskExecutor = Executors.newFixedThreadPool(thread_Count);
		
		for(final Object url : urlList){
			
			taskExecutor.execute(new Runnable(){

				public void run() {
					
					
					// TODO Auto-generated method stub
					try {
						
						HttpURLConnection connection = (HttpURLConnection)((URL) url).openConnection();
						connection.connect();
						actualStatuCode =  connection.getResponseCode();
			 		    connection.disconnect();
			 		    if(actualStatuCode != givenCode){
			 		    	urls.add(url);
			 		    }	 		    
					} 
					
					catch (IOException e) {
						// TODO Auto-generated catch block
     					e.printStackTrace();							
					 }	
					
					latch.countDown();				
				}			
				
			});		
		}
		
		try {
	        latch.await();  // The main Thread will wait until the value of 'count' is 0
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
		taskExecutor.shutdown();  //Shuts down the ExecutorService, that is, all the Threads created by the executor service will be killed.
		
		return urls;
		
	}
}
