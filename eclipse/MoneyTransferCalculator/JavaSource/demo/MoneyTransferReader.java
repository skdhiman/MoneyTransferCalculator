package demo;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class MoneyTransferReader {
	String fileName="";
	MoneyTransferVO voObj = new MoneyTransferVO();
	public static ArrayList<MoneyTransferVO> readExcelFile(Integer countrycode){
		ArrayList<MoneyTransferVO> feeList = new ArrayList<MoneyTransferVO>();
		MoneyTransferVO voObj;
		float from;
		float to;
		float fee;
		try{
			/** Creating Input Stream**/
	
			FileInputStream myInput = (FileInputStream) MoneyTransferReader.class.getClassLoader().getResourceAsStream("Excel/MoneyTransferFee.xls");
		    /** Create a POIFSFileSystem object**/
		    POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

		    /** Create a workbook using the File System**/
		     HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem); 
		     /** Get the first sheet from workbook**/
		     HSSFSheet mySheet;
		     if(countrycode==1){
		     mySheet = myWorkBook.getSheetAt(0);
		     }
		     else{
		    	 mySheet = myWorkBook.getSheetAt(1);
		     }

		     /** We now need something to iterate through the cells.**/
		       Iterator rowIter = mySheet.rowIterator();
		       while(rowIter.hasNext()){
		    	   int i = 1;
		    	   HSSFRow myRow = (HSSFRow) rowIter.next();
		    	   if(myRow.getRowNum()>1){
		    		   Iterator cellIter = myRow.cellIterator(); 
		    		   voObj = new MoneyTransferVO();
		    	          while(cellIter.hasNext()){
		    	        	  i++;
		    	        	  HSSFCell myCell = (HSSFCell) cellIter.next();
		    	        	  if(i == 4){
		    	        		  from =Float.parseFloat(myCell.toString());
		    	        		  voObj.setFrom(from);
		    	        		  
		    	        		  }
		    	        	  if(i == 5){
		    	        		  to =Float.parseFloat(myCell.toString());
		    	        		  voObj.setTo(to);
		    	          }
		    	        	  if(i == 6){
		    	        		  fee =Float.parseFloat(myCell.toString());
		    	        		  voObj.setMoneytransferFee(fee);
		    	        		  }
		    	        	  
		    	   }
		    	          feeList.add(voObj);
		       }

		}
		       
		
	}
		catch (Exception e){
	       	e.printStackTrace(); }
		return feeList;
}
	
	private static void printCellDataToConsole(Vector dataHolder) {

	    for (int i=0;i<dataHolder.size();i++) {                  
	                  Vector cellStoreVector=(Vector)dataHolder.elementAt(i);
	        for (int j=0; j< cellStoreVector.size();j++){
	            HSSFCell myCell = (HSSFCell)cellStoreVector.elementAt(j);
	            String stringCellValue = myCell.toString();
	            System.out.print(stringCellValue+"\t");
	        }
	        System.out.println();
	    }
	}
}
