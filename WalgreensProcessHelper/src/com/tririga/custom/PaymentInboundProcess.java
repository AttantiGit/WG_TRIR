package com.tririga.custom;



import java.util.*;

import org.apache.log4j.Logger;

import com.tririga.ws.dto.IntegrationField;

public class PaymentInboundProcess {
	private Logger log = Logger.getLogger(this.getClass());	
	public List < IntegrationField[]> process(List<Map<String, String>> al ){
		log.info(this.getClass()+" al = "+al);
		List < IntegrationField[]> dataRows = new ArrayList<>();
		try{

		      String filepaymentline = "";
		      String fileVendorID = "";
		      String fileAccountID = "";
		      String fileinvoiceDate = "";
		      String filecheckAmt = "";
		      String filecheckDate = "";
		      String filecheckNumber = "";
		      String filevendorName = "";
		      String fileinvoiceAmount = "";
		      String filerecordType = "";
		      String fileinvoiceID = "";
		      int counter = 1;
		         for (Map <String, String> hm : al) {
			          
		        	 log.info("\nList item # " + counter + ": \n");
		             // Now doing the iteration of the HashMap object 
		             for ( Map.Entry<String, String> entry : hm.entrySet() )
		             {

		                 //log.info( "Key : <" + entry.getKey() + "> Value : <" + entry.getValue() + ">");
		                 if(entry.getKey().equalsIgnoreCase("RecordType"))
		                     filerecordType = entry.getValue();
		                 if(entry.getKey().equalsIgnoreCase("VendorNumber"))
		                	 fileVendorID = entry.getValue();
		                 if(entry.getKey().equalsIgnoreCase("ItemText"))
		                	 fileinvoiceID = entry.getValue();	                 

		                 //cstRefDocNoTX - 3
		                 //cstRefKeyTX - 4
		                 //cstHouseBankTX - 5 
		                 if(entry.getKey().equalsIgnoreCase("RefKey3"))
		                	 filepaymentline = entry.getValue();	
		                 if(entry.getKey().equalsIgnoreCase("AccountID"))
		                	 fileAccountID = entry.getValue();
		                 
		                 if(entry.getKey().equalsIgnoreCase("CheckNumber"))
		                	 filecheckNumber = entry.getValue();
		                 
		                 if(entry.getKey().equalsIgnoreCase("CheckDate"))
		                	 filecheckDate = entry.getValue();
		                 
		                 if(entry.getKey().equalsIgnoreCase("CheckAmount"))
		                	 filecheckAmt = entry.getValue();
		                 
		                 if(entry.getKey().equalsIgnoreCase("Altpayee"))
		                	 filevendorName = entry.getValue();

		                 if(entry.getKey().equalsIgnoreCase("InvoiceAmount"))
		                	 fileinvoiceAmount = entry.getValue();
		                 
		                 if(entry.getKey().equalsIgnoreCase("InvoiceDate"))
		                	 fileinvoiceDate = entry.getValue();


		                // DateFormat dateFormatNeeded = new SimpleDateFormat("MM/dd/yyyy");
		                 //DateFormat userDateFormat = new SimpleDateFormat("yyyyMMdd");

		                 //Format checkdate
		                 if (!filecheckDate.equals(""))
		                 {
		                 	//Date date1 = userDateFormat.parse(filecheckDate);
		                 	//filecheckDate = dateFormatNeeded.format(date1);
		                	// 
		                	 
		                	 filecheckDate = "08/28/2018";
		                	 //filecheckDate =  new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyyMMdd").parse(filecheckDate));

		                 	//log.info(this.getClass()+">>>>>>>>>> filecheckDate " + filecheckDate);
		                 }

		                 if (!fileinvoiceDate.equals(""))
		                 {
		                 	//Date date2 = userDateFormat.parse(fileinvoiceDate);
		                 	fileinvoiceDate =  "08/28/2018";
		                	 //fileinvoiceDate =  new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyyMMdd").parse(fileinvoiceDate));
		                 	//log.info(this.getClass()+">>>>>>>>>> fileinvoiceDate " + fileinvoiceDate);
		                 }
		             }
		             
		             log.info("DTO INVOCATION Started");
	                 // Set the fields into cstPaymentInboundDTO  
	                 IntegrationField paymentLineItem = new IntegrationField();
	                 paymentLineItem.setName("cstPaymentLineItemIDTX");
	                 paymentLineItem.setValue(filepaymentline);

	                 log.info(this.getClass()+" paymentLineItem = " +paymentLineItem);

	                 IntegrationField recordType = new IntegrationField();
	                 recordType.setName("cstRecordTypeTX");
	                 recordType.setValue(filerecordType);

	                 IntegrationField VendorID = new IntegrationField();
	                 VendorID.setName("triVendorIdTX");
	                 VendorID.setValue(fileVendorID);

	                 IntegrationField invoiceID = new IntegrationField();
	                 invoiceID.setName("cstInvoiceIDTX");
	                 invoiceID.setValue(fileinvoiceID);

	                 IntegrationField accountID = new IntegrationField();
	                 accountID.setName("cstAccountIDTX");
	                 accountID.setValue(fileAccountID);

	                 IntegrationField checkNumber = new IntegrationField();
	                 checkNumber.setName("triCheckNumberTX");
	                 checkNumber.setValue(filecheckNumber);

	                 IntegrationField checkDate = new IntegrationField();
	                 checkDate.setName("triCheckDA");
	                 checkDate.setValue(filecheckDate);

	                 IntegrationField checkAmt = new IntegrationField();
	                 checkAmt.setName("triCheckAmtNU");
	                 checkAmt.setValue(filecheckAmt);

	                 IntegrationField vendorName = new IntegrationField();
	                 vendorName.setName("cstPayeeTX");
	                 vendorName.setValue(filevendorName);

	                 IntegrationField invoiceAmount = new IntegrationField();
	                 invoiceAmount.setName("cstAmountTX");
	                 invoiceAmount.setValue(fileinvoiceAmount);

	                 IntegrationField invoiceDate = new IntegrationField();
	                 invoiceDate.setName("cstProcessDA");
	                 invoiceDate.setValue(fileinvoiceDate);

	                 IntegrationField Status = new IntegrationField();
	                 Status.setName("Status");
	                 Status.setValue("Updated cstPaymentInboundDTO");      	


	                 log.info (" Begin cstPaymentInboundDTO ");
	                 IntegrationField[] generalInfoFieldsPayment = new IntegrationField[] {paymentLineItem,recordType,VendorID,invoiceID,accountID,checkNumber,checkDate,checkAmt,vendorName,invoiceAmount,invoiceDate,Status};
			         dataRows.add(generalInfoFieldsPayment);
		             // increment the counter
		             counter++;
		  
		         }
			  
		}catch (Exception e){
			e.printStackTrace();
		}	
		log.info("No Of rows"+dataRows.size());
		return dataRows;
	}
}
