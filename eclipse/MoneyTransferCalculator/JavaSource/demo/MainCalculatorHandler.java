/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package demo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

/**
 * Created by JBoss Tools
 */
@ManagedBean(name="mainCalculator")
@RequestScoped
public class MainCalculatorHandler {
	private static Float exchangeRate;
	private  String senderCurrency;
    private Integer countryCode=1;
	private float receiveMoney;
    private float returnValue;
    private static HashMap<String,String> currency;
    private List<SelectItem> currencyCodeList = new ArrayList<SelectItem>();
    private List<DiscountCouponVO> discountList=new ArrayList<DiscountCouponVO>();
    private List<MoneyTransferVO> moneyTransferList=new ArrayList<MoneyTransferVO>(); 
    private float youSend;
    private String discountCode;
    private float discountMoney;
    private float totalPayment;
    private String receiverCurrency="USD";
    private Boolean summaryRendered=false;
    private float totalmoneySend=0.0f;
    private String selectedItem;
    DecimalFormat format=new DecimalFormat("##.00");
    
public String getSelectedItem() {
	
	if(selectedItem!=null){
	    String valueArray[]=selectedItem.split("-");
	    exchangeRate=Float.valueOf(valueArray[0]);
	    senderCurrency=String.valueOf(valueArray[1]);
	}
		return selectedItem;
	}
	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}
  
	public float getTotalmoneySend() {
		return totalmoneySend;
	}
	public void setTotalmoneySend(float totalmoneySend) {
		this.totalmoneySend = totalmoneySend;
	}

	public Boolean getSummaryRendered() {
		return summaryRendered;
	}

	public void setSummaryRendered(Boolean summaryRendered) {
		this.summaryRendered = summaryRendered;
	}

	private float moneyTransferFee;
   
    

public String calculate(){
			
	youSend=receiveMoney*exchangeRate;
	youSend=Round(youSend,2);
	getMoneyTransferList();
	
	Float moneyTransfer = 0.0f;
	for(MoneyTransferVO money:moneyTransferList){
		
		if(receiveMoney>=money.getFrom() && receiveMoney<= money.getTo()){
			
			moneyTransfer=money.getMoneytransferFee();
			break;
}
	}
	moneyTransferFee=convertedXchnageRate(moneyTransfer);
	
	moneyTransferFee=Round(moneyTransferFee,2);

	//moneyTransferFee= Float.valueOf(arg0)String.format("%.2f", moneyTransferFee);
	
		return "next";
		
	}

public void continueButton(){
	totalmoneySend=youSend+moneyTransferFee-discountMoney;
	totalmoneySend=Round(totalmoneySend,2);
	summaryRendered=true;
}

private Float convertedXchnageRate(Float moneyTrans) {
	
	for(SelectItem select:currencyCodeList){
			if(select.getLabel().equalsIgnoreCase("USD")){
				String valueArray[]=((String) select.getValue()).split("-");
				Float usdRate= Float.valueOf(valueArray[0]);
				float moneyRatefromUSDtoCurrentCompny=moneyTrans*usdRate;
				
				float conversionFromCurentCompanyToSenCompany=1/exchangeRate;
				
				moneyTrans=conversionFromCurentCompanyToSenCompany*moneyRatefromUSDtoCurrentCompny;
}
			
		}
	
	return moneyTrans;
}

public void changecountryCode(AjaxBehaviorEvent event){
	HtmlSelectOneMenu uc = (HtmlSelectOneMenu)event.getComponent();
	this.countryCode = new Integer(uc.getSubmittedValue().toString());
	getCurrencyCodeList();
		if(countryCode==1){
		receiverCurrency="USD";	
	}
	else{
		receiverCurrency="ZAR";
	}
}


public Float getExchangeRate() {
	return exchangeRate;
}

public void setExchangeRate(Float exchangeRate) {
	this.exchangeRate = exchangeRate;
}



public Float discountEnter(){
	getDiscountList();
    for(DiscountCouponVO coupon:discountList){
		
		if(discountCode.equalsIgnoreCase(coupon.getDiscountCode())){
		float moneydiscountedInUSD=coupon.getDiscountAmount();
        discountMoney =convertedXchnageRate(moneydiscountedInUSD);
        discountMoney=Round(discountMoney,2);
		break;
}
		
		}

	return discountMoney;
	}

public String getReceiverCurrency() {
	return receiverCurrency;
}

public void setReceiverCurrency(String receiverCurrency) {
	this.receiverCurrency = receiverCurrency;
}

     public List<SelectItem> getCurrencyCodeList() {
    	currencyCodeList.clear();
		 HashMap<Float,String> dataHolder=CurrencyData.readExcelFile(countryCode);
   	 
		 Set set=dataHolder.entrySet();
		 
		 Iterator i=set.iterator();
		 while(i.hasNext()){
			 
			 Map.Entry me=(Map.Entry)i.next();
		
				SelectItem selItem = new SelectItem(me.getKey()+"-"+me.getValue().toString(),me.getValue().toString());
				currencyCodeList.add(selItem);
			 
		 }
		 
		return currencyCodeList;
	}
	
   public float getYouSend() {
			return youSend;
		}

		public void setYouSend(float youSend) {
			this.youSend = youSend;
		}

		public float getMoneyTransferFee() {
			return moneyTransferFee;
		}

		public void setMoneyTransferFee(float moneyTransferFee) {
			this.moneyTransferFee = moneyTransferFee;
		}

		public String getDiscountCode() {
			return discountCode;
		}

		public void setDiscountCode(String discountCode) {
			this.discountCode = discountCode;
		}

		public float getDiscountMoney() {
			return discountMoney;
		}

		public void setDiscountMoney(float discountMoney) {
			this.discountMoney = discountMoney;
		}

		public float getTotalPayment() {
			return totalPayment;
		}

		public void setTotalPayment(float totalPayment) {
			this.totalPayment = totalPayment;
		}




   public void setCurrencyCodeList(List<SelectItem> currencyCodeList) {
		this.currencyCodeList = currencyCodeList;
	}

	public MainCalculatorHandler(){
    	
    }
  
	public static HashMap<String, String> getCurrency() {
		return currency;
	}

public static void setCurrency(HashMap<String, String> currency) {
		MainCalculatorHandler.currency = currency;
	}

public float getReturnValue() {
		return returnValue;
	}
	
public void setReturnValue(float returnValue) {
		this.returnValue = returnValue;
	}

	public float getReceiveMoney() {
		return receiveMoney;
	}

	public void setReceiveMoney(float receiveMoney) {
		this.receiveMoney = receiveMoney;
	}

	public Integer getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}

    public List<DiscountCouponVO> getDiscountList() {
    	discountList=DiscountCouponReader.readFileData();
		return discountList;
	}

	public void setDiscountList(List<DiscountCouponVO> discountList) {
		this.discountList = discountList;
	}
	
    public List<MoneyTransferVO> getMoneyTransferList() {
    	
    	moneyTransferList=MoneyTransferReader.readExcelFile(countryCode);
		return moneyTransferList;
	}

	public void setMoneyTransferList(List<MoneyTransferVO> moneyTransferList) {
		this.moneyTransferList = moneyTransferList;
	}
	
	public String getSenderCurrency() {
		return senderCurrency;
	}
	public void setSenderCurrency(String senderCurrency) {
		this.senderCurrency = senderCurrency;
	}
	
	public static float Round(float rval,int rpl){
		float p=(float)Math.pow(10, rpl);
		rval=rval*p;
		float tmp=Math.round(rval);
		
		return (float)tmp/p;
		
	}
	
}