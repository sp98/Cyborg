package com.Santosh.Cyborg.MyFiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.Santosh.Cyborg.Utils.Log;

public class FileReader {

	HashMap <String, ArrayList<ArrayList<String>>> allSheetData = null;     // hashmap with data as String - ArrayList of ArrayList as Key-Value pair.
	ArrayList<ArrayList<String>> sheetData = null;       //an arrayList of ArrayList
	ArrayList<String> sheetRowData = null;			     //an arrayList
	
	/**Reads a sheet in an Excel file. Copies the data in an arrayList or ArrayList ( Two-dimensional array)
	 * @param xlReader - Workbook object of the file that we want to read.
	 * @param sheetName - The particular sheet name of the file that we want to read.
	 * @return an arrayList of arrayList containing all the data of the sheet
	 * in 2D array format. Returns null if the sheet name is not present on in the file.
	 */
	public ArrayList<ArrayList<String>> getWorkSheetData(Workbook xlReader, String sheetName){
		
		
		/*check if the sheet name is present in the file or not */
		if(!isSheetPresent(xlReader, sheetName)){
			Log.error("Sheet not found in the file");
			return null;
		}
		
		
		Sheet sheet = xlReader.getSheet(sheetName);        //get the Sheet object for the SheetName
		sheetData = new ArrayList<ArrayList<String>>();    //initialize that arrayList of ArrayList		
		
		for(Row row : sheet){
			
			sheetRowData = new ArrayList<String>();        // intialize the arrayList to store cell values for each row.
			
			for(Cell cell : row){
				cell.setCellType(cell.CELL_TYPE_STRING);   // change all the cell values to String.			
				 if(cell!=null){	 
					 sheetRowData.add(cell.getStringCellValue());   //add the cell data to 'Cell
					 continue; 
				 }			
			} //end of inner for loop to get data in each row.
			
			sheetData.add(sheetRowData);                     // add the arrayList for each row the another arrayList (2D array).
			
		} //end of outer for loop to get the data for the entire sheet.
		
	 return sheetData;
		
	}
	
	
	/**Read multiple sheets in an Excel file. Copies the data in an arrayList or ArrayList ( Two-dimensional array)
	 * @param xlReader - Workbook object of the file that we want to read.
	 * @param sheetNames - String Array for sheetNames
	 * @return an arrayList of arrayList containing all the data of the sheet
	 * in 2D array format. Returns null if the sheet name is not present on in the file.
	 */
	public HashMap<String, ArrayList<ArrayList<String>>> getWorkSheetData(Workbook xlReader, String [] sheetNames){
					
		if(xlReader == null){
			Log.error("Xlready object is not valid");
			return null;
		}
		
		allSheetData = new HashMap<String, ArrayList<ArrayList<String>>>();     //Initializing the hashMap to store all sheet data.
		
		for(String sheetName : sheetNames){                       // Loop 1 - main loop to iterate through each list in the sheet.
			
			sheetData = new ArrayList<ArrayList<String>>();    //initialize that arrayList of ArrayList	
			
			if(!isSheetPresent(xlReader, sheetName)){
				Log.error("Sheet not found in the file");
				sheetRowData = new ArrayList<String>(); 
				sheetRowData.add("Sheet Not found in this file");
				sheetData.add(sheetRowData);
				allSheetData.put(sheetName,sheetData);	
				continue;
			}
			 
			Sheet sheet = xlReader.getSheet(sheetName);        //get the Sheet object for the SheetName
			
			
			    for(Row row : sheet){                          // Loop 2- to iterator over each row in sheet.
				
				   sheetRowData = new ArrayList<String>();        // initialize the arrayList to store cell values for each row.
				
				      for(Cell cell : row){                       // Loop 3 - to iterator over each cell in a row.
					      cell.setCellType(cell.CELL_TYPE_STRING);   // change all the cell values to String.			
					      if(cell!=null){	 
						     sheetRowData.add(cell.getStringCellValue());   //add the cell data to 'Cell
						     continue; 
					 }			
				} //Loop 3 ends here.
				
				sheetData.add(sheetRowData);                     // add the arrayList for each row the another arrayList (2D array).
				
			} //Loop 2- ends here
			
		   allSheetData.put(sheetName, sheetData);
		}  //Loop 1- ends here.
		
		return allSheetData;
	}
	
	
	
	/**Read multiple sheets in an Excel file based on the column headers.
	 * Copies the data in an arrayList or ArrayList ( Two-dimensional array)
	 * @param xlReader - Workbook object of the file that we want to read.
	 * @param sheetNames - String Array for sheetNames
	 * @return a HashMap containing data for each sheet. Returns null if the sheet name is not present on in the file.
	 */
	public HashMap<String, ArrayList<ArrayList<String>>> getWorkSheetData(Workbook xlReader, String fileName, 
			 String [] sheetNames , String [] colHeaders){
					
		if(xlReader == null){
			Log.error("Xlready object is not valid");
			return null;
		}
		
		allSheetData = new HashMap<String, ArrayList<ArrayList<String>>>();     //Initializing the hashMap to store all sheet data.
		
		First:
		for(String sheetName : sheetNames){                       // Loop 1 - main loop to iterate through each list in the sheet.
			
			sheetData = new ArrayList<ArrayList<String>>();    //initialize that arrayList of ArrayList	
			
			if(!isSheetPresent(xlReader, sheetName)){
				Log.error("Sheet not found in the file");
				sheetRowData = new ArrayList<String>(); 
				sheetRowData.add("Sheet Not found in this file");
				sheetData.add(sheetRowData);
				allSheetData.put(sheetName,sheetData);	
				continue;
			}
			 
			Sheet sheet = xlReader.getSheet(sheetName);        //get the Sheet object for the SheetName
			LinkedHashMap<String , Integer> colIndexMap = new LinkedHashMap<String, Integer>();
			
			Second:                                                      
			for(String colName : colHeaders){                  //Loop 2   - To loop through each header name to find its index.
				int colIndex = getCellIndex(sheet, colName);
				if(colIndex==-1){
					Log.error("One or more column Headers are missing in sheet " + "\""+sheetName+"\"" + " in file " + 
					         "\""+fileName+"\"");
					//Move back to first for
				}
					
						//Add Error Message to this file
				colIndexMap.put(colName, colIndex);
			}                                                 // Loop 2 ends here.
			
			Third:
			for(int i = 1; i<sheet.getPhysicalNumberOfRows();i++){           //Loop 3 - To iterate each Row in current Cell.
				sheetRowData = new ArrayList<String>();
				  Row row = sheet.getRow(i);
				  
				  Fourth: 
				  for(String key: colIndexMap.keySet()){                     //Loop 4 - To iterate through the hashMap of each cell.
					  
					  if(key.equals("Name")){
						  //Log.info("checkName");
					  }
					  
					  fifth:
					  for(int j=0; j<row.getPhysicalNumberOfCells(); j++){     //Loop 5 - To iterate each cell in current Row.
						   
						   Cell cell  = row.getCell(j);
						   if(cell!=null){
							   cell.setCellType(cell.CELL_TYPE_STRING);		   
							   if(j == colIndexMap.get(key)){			  
								  sheetRowData.add(cell.getStringCellValue().isEmpty()?"N/A":cell.getStringCellValue());
							      continue;
							   }
							   if(j == colIndexMap.get(key)){
								   sheetRowData.add(cell.getStringCellValue().isEmpty()?"N/A":cell.getStringCellValue());
								   
								   continue;
							   }
								   
							   if(j == colIndexMap.get(key)){
								   sheetRowData.add(cell.getStringCellValue().isEmpty()?"N/A":cell.getStringCellValue());
								   continue;
							   }
								  
							   if(j == colIndexMap.get(key)){
								   sheetRowData.add(cell.getStringCellValue().isEmpty()?"N/A":cell.getStringCellValue());
								   continue;
							   }
								   
							   if(j == colIndexMap.get(key)){
								   sheetRowData.add( cell.getStringCellValue().isEmpty()?"N/A":cell.getStringCellValue());
								   continue;
							   }
								   
						   }
						   
				  }   //Loop 5 ends here.
				   
						   
				   } //Loop 3 ends here
				sheetData.add(sheetRowData);
				allSheetData.put(sheetName, sheetData);
				
			}  //Loop 3 - ends here.
			  
			
		}//Loop 1 - ends here.
		
		return allSheetData;
	}
	
	
	/**Read multiple sheets in an Excel file based on the column headers on particular row.
	 * Copies the data in an arrayList or ArrayList ( Two-dimensional array)
	 * @param xlReader - Workbook object of the file that we want to read.
	 * @param sheetNames - String Array for sheetNames
	 * @return a HashMap containing data for each sheet. Returns null if the sheet name is not present on in the file.
	 */
	public HashMap<String, ArrayList<ArrayList<String>>> getWorkSheetData(Workbook xlReader, String fileName, 
			 String [] sheetNames , String [] colHeaders, int colHeaderRow){
					
		if(xlReader == null){
			Log.error("Xlready object is not valid");
			return null;
		}
		
		allSheetData = new HashMap<String, ArrayList<ArrayList<String>>>();     //Initializing the hashMap to store all sheet data.
		
		First:
		for(String sheetName : sheetNames){                       // Loop 1 - main loop to iterate through each list in the sheet.
			
			sheetData = new ArrayList<ArrayList<String>>();    //initialize that arrayList of ArrayList	
			
			if(!isSheetPresent(xlReader, sheetName)){
				Log.error("Sheet not found in the file");
				sheetRowData = new ArrayList<String>(); 
				sheetRowData.add("Sheet Not found in this file");
				sheetData.add(sheetRowData);
				allSheetData.put(sheetName,sheetData);	
				continue;
			}
			 
			Sheet sheet = xlReader.getSheet(sheetName);        //get the Sheet object for the SheetName
			LinkedHashMap<String , Integer> colIndexMap = new LinkedHashMap<String, Integer>();
			
			Second:                                                      
			for(String colName : colHeaders){                  //Loop 2   - To loop through each header name to find its index.
				int colIndex = getCellIndex(sheet, colName, colHeaderRow);
				if(colIndex==-1){
					Log.error("One or more column Headers are missing in sheet " + "\""+sheetName+"\"" + " in file " + 
					         "\""+fileName+"\"");
					//Move back to first for
				}
					
						//Add Error Message to this file
				colIndexMap.put(colName, colIndex);
			}                                                 // Loop 2 ends here.
			
			Third:
			for(int i = colHeaderRow+1; i<sheet.getPhysicalNumberOfRows();i++){           //Loop 3 - To iterate each Row in current Cell.
				sheetRowData = new ArrayList<String>();
				  Row row = sheet.getRow(i);
				  
				  Fourth: 
				  for(String key: colIndexMap.keySet()){                     //Loop 4 - To iterate through the hashMap of each cell.
					  
					  if(key.equals("Name")){
						  //Log.info("checkName");
					  }
					  
					  fifth:
					  for(int j=0; j<row.getPhysicalNumberOfCells(); j++){     //Loop 5 - To iterate each cell in current Row.
						   
						   Cell cell  = row.getCell(j);
						   if(cell!=null){
							   cell.setCellType(cell.CELL_TYPE_STRING);		   
							   if(j == colIndexMap.get(key)){			  
								  sheetRowData.add(cell.getStringCellValue().isEmpty()?"N/A":cell.getStringCellValue());
							      continue;
							   }
							   if(j == colIndexMap.get(key)){
								   sheetRowData.add(cell.getStringCellValue().isEmpty()?"N/A":cell.getStringCellValue());
								   
								   continue;
							   }
								   
							   if(j == colIndexMap.get(key)){
								   sheetRowData.add(cell.getStringCellValue().isEmpty()?"N/A":cell.getStringCellValue());
								   continue;
							   }
								  
							   if(j == colIndexMap.get(key)){
								   sheetRowData.add(cell.getStringCellValue().isEmpty()?"N/A":cell.getStringCellValue());
								   continue;
							   }
								   
							   if(j == colIndexMap.get(key)){
								   sheetRowData.add( cell.getStringCellValue().isEmpty()?"N/A":cell.getStringCellValue());
								   continue;
							   }
								   
						   }
						   
				  }   //Loop 5 ends here.
				   
						   
				   } //Loop 3 ends here
				sheetData.add(sheetRowData);
				allSheetData.put(sheetName, sheetData);
				
			}  //Loop 3 - ends here.
			  
			
		}//Loop 1 - ends here.
		
		return allSheetData;
	}
	
	
	
	
	/**
	 * Scan the excel sheet to find the position of a particular column header.
	 * 
	 * @param sheet - WorkBook Sheet object for the currently running Sheet
	 * @param columnHeader - String value of the column header position of which needs to be found.
	 * @return - integer value of the column header position in the excel sheet.
	 */
	
	public int getCellIndex(Sheet sheet , String columnHeader){
		
		for(Cell cell : sheet.getRow(0)){   // Loop 1- to find the column index position of all the mandatory Column headers starts here
	    	 cell.setCellType(Cell.CELL_TYPE_STRING);
	 
	    	if(cell.getStringCellValue().trim().equalsIgnoreCase(columnHeader)){
	    		return cell.getColumnIndex();
	    	} 
	    			
	 }// Loop 1 ends here
		
		 return 0;
	}
	
	
	/**
	 * Scan the excel sheet to find the position of a particular column header.
	 * 
	 * @param sheet - WorkBook Sheet object for the currently running Sheet
	 * @param columnHeader - String value of the column header position of which needs to be found.
	 * @return - integer value of the column header position in the excel sheet.
	 */
	
	public int getCellIndex(Sheet sheet , String columnHeader , int colHeaderRow){
		
		for(Cell cell : sheet.getRow(colHeaderRow)){   // Loop 1- to find the column index position of all the mandatory Column headers starts here
	    	 cell.setCellType(Cell.CELL_TYPE_STRING);
	 
	    	if(cell.getStringCellValue().trim().equalsIgnoreCase(columnHeader)){
	    		return cell.getColumnIndex();
	    	} 
	    			
	 }// Loop 1 ends here
		
		 return 0;
	}
	
	
	
	
	
	/**
	 * Scan the excel sheet to check if the particular sheet name provided in the config is present in the file
	 * 
	 * @param xlReader: Workbook object of the currently running file. 
	 * @param SheetName - Name of the sheet the presence of which needs to be tested.
	 * @return True if the sheet in present and false otherwise.
	 */
	public boolean isSheetPresent(Workbook xlReader, String sheetName){
		
		 boolean sheetFound= false;
		
		   for( int j=0; j<xlReader.getNumberOfSheets(); j++){
			   		  
			   if(sheetName.trim().equals(xlReader.getSheetName(j))){
				   sheetFound = true;			  
			   }		  
		   }
		   
		 return sheetFound;
		
	}
	
}
