package demo;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class CurrencyData {

	String fileName="";

    //Read an Excel File and Store in a Vector
    //Vector dataHolder=readExcelFile();
    //Print the data read
   // printCellDataToConsole(dataHolder);

public static HashMap<Float, String> readExcelFile(Integer countrycode)
{
    /** --Define a Vector
        --Holds Vectors Of Cells
     */
   
    HashMap<Float, String> currencyHashMap=new HashMap<Float, String>();
	  String currency = null;
	  Float rate=null;

    try{
    /** Creating Input Stream**/
    	Properties props=new Properties();
    	props.load(CurrencyData.class.getClassLoader().getResourceAsStream("ExcelPath.properties"));
    	
    	
    	
    FileInputStream myInput = (FileInputStream) CurrencyData.class.getClassLoader().getResourceAsStream("Excel/Currency.xls");

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
    	  int i=0;
    
          HSSFRow myRow = (HSSFRow) rowIter.next();
          if(myRow.getRowNum()>0){
          Iterator cellIter = myRow.cellIterator();
          Vector cellStoreVector=new Vector();
          while(cellIter.hasNext()){
        	  i++;
              HSSFCell myCell = (HSSFCell) cellIter.next();
             if(i==2){
            	 currency=myCell.toString();
             }
             
             if(i==4){
            	 rate=Float.valueOf(myCell.toString());
             }
          }
          currencyHashMap.put(rate,currency);
          
      }}
    }catch (Exception e){
    	e.printStackTrace(); }
    return currencyHashMap;
}

}
	
	

