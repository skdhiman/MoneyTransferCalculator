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

public class DiscountCouponReader {
	String fileName="";
	public static ArrayList<DiscountCouponVO> readFileData(){
		ArrayList<DiscountCouponVO> discountList = new ArrayList<DiscountCouponVO>();
		DiscountCouponVO couponVO;
		String promotion;
		String code;
		float discountAmount;
		try{
			/** Creating Input Stream**/
		    
		    
		  //
		  	//Properties props=new Properties();
	    	//props.load(CurrencyData.class.getClassLoader().getResourceAsStream("ExcelPath.properties"));
	    	FileInputStream myInput = (FileInputStream) DiscountCouponReader.class.getClassLoader().getResourceAsStream("Excel/Discount.xls");
		    /** Create a POIFSFileSystem object**/
		    POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

		    /** Create a workbook using the File System**/
		     HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
		     /** Get the first sheet from workbook**/
		     HSSFSheet mySheet = myWorkBook.getSheetAt(0);

		     /** We now need something to iterate through the cells.**/
		       Iterator rowIter = mySheet.rowIterator();
		       while(rowIter.hasNext()){
		    	   int i = 0;
		    	   HSSFRow myRow = (HSSFRow) rowIter.next();
		    	   if(myRow.getRowNum()>0){
		    		   couponVO = new DiscountCouponVO();
		    		   Iterator cellIter = myRow.cellIterator();
		    		   while(cellIter.hasNext()){
		    	        	  i++;
		    	        	  HSSFCell myCell = (HSSFCell) cellIter.next();
		    	        	  if(i == 1){
		    	        		  promotion = myCell.toString();
		    	        		  couponVO.setPromotion(promotion);
		    	        	  }
		    	        	  if(i == 2){
		    	        		  code  = myCell.toString();
		    	        		  couponVO.setDiscountCode(code);
		    	        	  }
		    	        	  if(i == 3){
		    	        		  discountAmount = Float.parseFloat(myCell.toString());
		    	        		  couponVO.setDiscountAmount(discountAmount);
		    	        	  }
		    	   }
		    		   discountList.add(couponVO);
		       }
		}
	}
		catch (Exception e){
	       	e.printStackTrace(); }
		return discountList;

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
