package com.tririga.custom;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import com.tririga.ws.TririgaWS;
import com.tririga.custom.CSVUtils;

public class ADR7OutboundProcess {
	private Logger log = Logger.getLogger(this.getClass());	
	public void process(TririgaWS tws,FileWriter writer,JSONArray invoiceHeaderRecordData,JSONArray pmtLnItemsItemrecordData ){
		try{
 
			try{
							
				log.info(this.getClass()+" Step #2" + pmtLnItemsItemrecordData.length());
			
				String InvoiceNumber = new SimpleDateFormat("yyyyMMddHHmmssss").format(new Date());		
				String currendate = new SimpleDateFormat("yyyyMMdd").format(new Date());
				
				for(int i=0;i<invoiceHeaderRecordData.length();i++ ){
					log.info(this.getClass()+" Inside the For loop " );
						JSONObject invRecord = invoiceHeaderRecordData.getJSONObject(i);
						String BatchID = invRecord.getString("Invoice Number");
						String VendorNumber = invRecord.getString("Vendor Number");
						String currentDate = currendate;
					    String CompanyCode = invRecord.getString("Company Code");
					    String ProfitCenter = invRecord.getString("Profit Center");
					    String InvoiceType = "KT";
    
					    String InvoiceCurrentDate = currendate;
					    String PostingCurrentDate = currendate;
					    String TransDate = "";
					    String VendorName = "";
					    String VendorName2 = "";
					    String LegalName = "";
					    String LegalName2 = "";
					    String SSNTaxID = "";
					    String FEINTaxID = "";
					    String Address1 = "";
					    String Address2 = "";
					    String PObox = "";
					    String POPostalCode = "";
					    String City = "";
					    String PostalCode = "";
					    String Country = "";
					    String State = "";
					    String BankNumber = "";
					    String BankAcct = "";
					    String RefKey1 = "";
					    String CalculatedTotal = "0";
					    String InvoiceCurrency = "USD";
					    String RefKey2 = "";
					    String SAPPaymentTermCode = invRecord.getString("Term Code");
					    String BLineDate = "";
					    String DSCT_DAYS1 = "";
					    String DSCT_PCT1 = "";
					    String DSCT_DAYS11 = "";
					    String DSCT_DAYS2 = "";
					    String DSCT_PCT2 = "";
					    String PaymentMethod = "";
					    String PayBlock = "";
					    String AllocNumber = "";
					    String ItemText = "";
					    String Field4 = "";
					    String Field3 = "";
					    String ExternalAPVendorNumber = invRecord.getString("Vendor Number");
					    String BankID = invRecord.getString("Cash Code");
					    String HouseBankAccntID = invRecord.getString("Cash Code");
					    String SourceSystemIdentifier = "LRD";
					    String WT_Type = "";
					    String WT_Code = "";

						try{
							long INV = Long.valueOf(InvoiceNumber);				   
							for(int L=0;L<pmtLnItemsItemrecordData.length();L++ ){
								JSONObject pmtLnRecord = pmtLnItemsItemrecordData.getJSONObject(L);
								log.info(this.getClass()+" Step #9 - Starting the Payment Line Items Detail");
								if(BatchID.equalsIgnoreCase(pmtLnRecord.getString("Invoice Number")) ){
								   String paymentLineID = pmtLnRecord.getString("Payment Line ID");
									String prevLineid = "";
									String SequentialNumber = "1";
								    String COMP_Code = "";
								    String CostCenter =  "";
								    String DetailProfitCenter =  "";
								    String SAPAccount =  pmtLnRecord.getString("Contract Number");
								    String ITEM_Text =  pmtLnRecord.getString("Description");
								    String REF_KEY_1 =  "";
								    String DetailPLItemID =  "";
								    String AllocationNumber =  "";
								    String DetailInvoiceCurrency = "USD";
								    String ExpectedCashwithTax = pmtLnRecord.getString("Expected Cash with Tax");
								    String WBS_Element =  "";
								    String ORDERID =  "";
									log.info(this.getClass()+" Step #10 -Middle of the Detail");
									
									String UOM = pmtLnRecord.getString("UOM");
									String GLAccount = pmtLnRecord.getString("GL Account");
									String GLAccount2 = pmtLnRecord.getString("Accounting Cost Code");
									if (GLAccount.equalsIgnoreCase(""))
									{
										GLAccount = GLAccount2;
									}
								
									log.info(this.getClass()+" Step #11 - Almost towards the end of Detail");
									 
									 if (UOM.equalsIgnoreCase("US Dollars"))
									 {
										 UOM = "US";
									 }

									 if(prevLineid != paymentLineID){
						               CSVUtils.writeLine(writer, Arrays.asList("H", 
    		                                 	VendorNumber,
    		                                 	String.valueOf(INV),      		                               
    		                                    currentDate,
    		                                    CompanyCode,
    		                                    ProfitCenter,
    		                                    InvoiceType,
    		                                    InvoiceCurrentDate,
    		                                    PostingCurrentDate,
    		                                    TransDate,
    		                                    VendorName,
    		                                    VendorName2,
    		                                    LegalName,
    		                                    LegalName2,
    		                                    SSNTaxID,
    		                                    FEINTaxID,
    		                                    Address1,
    		                                    Address2,
    		                                    PObox,
    		                                    POPostalCode,
    		                                    City,
    		                                    PostalCode,
    		                                    Country,
    		                                    State,
    		                                    BankNumber,
    		                                    BankAcct,
    		                                    RefKey1,
    		                                    CalculatedTotal,
    		                                    InvoiceCurrency,
    		                                    RefKey2,
    		                                    paymentLineID,
    		                                    SAPPaymentTermCode,
    		                                    BLineDate,
    		                                    DSCT_DAYS1,
    		                                    DSCT_PCT1,
    		                                    DSCT_DAYS11,
    		                                    DSCT_DAYS2,
       		                                    DSCT_PCT2,
       		                                    PaymentMethod,
       		                                    PayBlock,
       		                                    AllocNumber,
       		                                    ItemText,
       		                                    Field4,
       		                                    Field3,
       		                                    ExternalAPVendorNumber,
       		                                    BankID,
       		                                    HouseBankAccntID,
       		                                    SourceSystemIdentifier,
       		                                    WT_Type,
       		                                    WT_Code),'~');
						               
						               CSVUtils.writeLine(writer, Arrays.asList("D",
												SequentialNumber,
												COMP_Code,
												CostCenter,
												DetailProfitCenter,
												SAPAccount,
												ITEM_Text,
												REF_KEY_1,
												DetailPLItemID,
												AllocationNumber,
												DetailInvoiceCurrency,
												ExpectedCashwithTax,
												WBS_Element,
												ORDERID
												),'~');
								        prevLineid = paymentLineID;
								        INV = INV + 1;
									 }else{
										System.out.println("you are here"); 
									 }
								log.info(this.getClass()+ " "+SequentialNumber+COMP_Code+CostCenter+DetailProfitCenter+SAPAccount+ITEM_Text+REF_KEY_1+DetailPLItemID+AllocationNumber+DetailInvoiceCurrency+WBS_Element+ORDERID);
							
						} //if invoice number is the same
					}
				
					}catch(Exception e){
						log.info(this.getClass()+" >>>>>>>>>>triPayment Line Item query error ");
						e.printStackTrace();
					}
				}
			}catch(Exception e){
				log.info(this.getClass()+" >>>>>>>>>>Invoice Header query error ");
				e.printStackTrace();
			}
	        writer.flush();
	        writer.close();	
			  
		}catch (Exception e){
			e.printStackTrace();
		}	
	}
}
