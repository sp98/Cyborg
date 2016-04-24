package com.Santosh.Cyborg.MyFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.Santosh.Cyborg.Utils.Log;

public class FileHandler {
	
	
	
	/**
	 * Checks if the file is present in the current directory.
	 * 
	 * @param directory - the path where the file has be searched in
	 * @param fileName - The file name to be searched
	 * @return File object if the file is found, else NULL
	 */
	public File getFile(String directory, String fileName) {
		File f = new File(directory);
		File[] listOfFiles = f.listFiles();

		for (File file : listOfFiles) {
			/*
			 * check if the file with specific name is present in the directory.
			 * Also ignores the version of file that is already opened
			 */
			if (file.getName().contains(fileName)
					&& (!file.getName().contains("~lock"))) {
				Log.info("File " + fileName + " found at " + file);
				return file;
			}
			
			else
				Log.error("File " + fileName + " NOT found at " + file);

		}
		
		return null;

	}
	
	
	
	/**
	 * Checks the file extension then then creates a Workbook object for that file.
	 * 
	 * @param file - File object of the currently running File
	 * @param FileName - Name of the currently running file.
	 * @param inputStream - inputStream object to interact with the file.
	 * @return - a WorkBook object of the File. 
	 */
	public Workbook getXLReader(File file, String fileName){
		
		Workbook xlReader= null;
		
		/*create Workbook Reader object only of the File sent as argument is not Null */
		if(file != null){
			
			try {
				FileInputStream inputStream = new FileInputStream(file);
				//String fileExtension = fileName.substring(fileName.indexOf("."));
				String fileExtension = file.getName().substring(file.getName().indexOf("."));
				if(fileExtension.equalsIgnoreCase(".xls")){
					
					try {
						xlReader = new HSSFWorkbook(inputStream);
						Log.debug("File " + fileName + " has .xls extension ");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				else if(fileExtension.equalsIgnoreCase(".xlsx")){
					
					try {
						xlReader = new XSSFWorkbook(inputStream);
						Log.debug("File " + fileName + " has .xlsx extension ");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return xlReader;
		
		
		
	}

}
