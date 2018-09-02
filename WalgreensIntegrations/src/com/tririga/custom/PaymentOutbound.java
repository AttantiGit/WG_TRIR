package com.tririga.custom;

import java.io.FileWriter;

import java.text.SimpleDateFormat;
import java.util.*;


import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.kv.json.ResultParsing;
import com.kv.querytrigger.QueryParam;
import com.kv.querytrigger.QueryTrigger;

import com.tririga.pub.workflow.CustomBusinessConnectTask;
import com.tririga.pub.workflow.Record;
import com.tririga.ws.TririgaWS;
import com.tririga.ws.dto.IntegrationField;
import com.tririga.ws.dto.IntegrationRecord;
import com.tririga.ws.dto.IntegrationSection;
import com.tririga.ws.dto.ResponseHelper;
import com.tririga.ws.dto.ResponseHelperHeader;

public class PaymentOutbound implements CustomBusinessConnectTask{
	// This was developed for Walgreens I Lease Implementation in May 2018. The Batch process payments including the RE invoices (reports are filtered on Issued status and for the Process Payments issued on a daily basis)
	// The sum of all the paymentline items for the corresponding lease is created as a new row into cstBatchHeaderDTO using a custom workflow
	// The file generation process is based on a scheduler generated and the file is "~" separator.
	
	
	private Logger log = Logger.getLogger(this.getClass());	
	@Override
	public boolean execute(TririgaWS tws, long userId, Record[] records) {
		log.info(this.getClass()+" >>>>>>>>>>Code Executing inside Custom Task ");

        try{
    		String integration = "Payment Outbound";
    		String FileName = null;
    		String FileLocation = "";
        	tws.register(userId);
            FileWriter writer = null;
            String IntegrationName = "";
    		try{
    			QueryParam configQueryParm = new QueryParam();

    			QueryTrigger configqueryTrigger = new QueryTrigger(tws, 1, 10);
    			//////configuration query
    			ResultParsing configResultParsing = new ResultParsing(configqueryTrigger.triggerQuery(configQueryParm.getJsonQuery("Data Utilities", "cstIntergrationConfig", "cst - cstIntegrationConfig - Get Config")));
    			JSONArray configRecordData = configResultParsing.createQueryJsonArray();
    			//log.info(this.getClass()+">>>>>>>>>>B4 configRecordData.length()");
    			for(int i=0;i<configRecordData.length();i++ ){
    				JSONObject configRecord = configRecordData.getJSONObject(i);
    				IntegrationName = configRecord.getString("IntegrationName");
    				if(IntegrationName.equalsIgnoreCase(integration)){
    					FileName = configRecord.getString("FileName");
    					FileName=FileName+"."+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    					String IntegrationType = configRecord.getString("IntegrationType");
    					FileLocation = configRecord.getString("FileLocation");
        				log.info(this.getClass()+"IntegrationName = "+IntegrationName);
    					log.info(this.getClass()+"FileName = "+FileName);
    					log.info(this.getClass()+"IntegrationType = "+IntegrationType);
    					log.info(this.getClass()+"FileLocation = "+FileLocation);

    					String csvFile = FileLocation+"/"+FileName;
    					log.info(this.getClass()+"csvFile = "+csvFile);
    					writer = new FileWriter(csvFile);
    				}
    			}
    			
				QueryParam param = new QueryParam();
				QueryTrigger queryTrigger = new QueryTrigger(tws, 1, 10000);
				
			    //////Header query
			    ResultParsing invoiceResultParsing = new ResultParsing(queryTrigger.triggerQuery(param.getJsonQuery("Data Utilities", "cstBatchHeaderDTO", "cst - BatchHeaderDTO - Header - Integration")));
				log.info(this.getClass()+" invoiceRecordData.length()");
				JSONArray invoiceHeaderRecordData = invoiceResultParsing.createQueryJsonArray();
							
				log.info(this.getClass()+" Step #1 " + invoiceHeaderRecordData.length());
		         //////detail query
				QueryParam param1 = new QueryParam();
				QueryTrigger queryTrigger1 = new QueryTrigger(tws, 1, 30000);
				ResultParsing pmtLnItemsParsing = new ResultParsing(queryTrigger1.triggerQuery(param1.getJsonQuery("triPayment", "triProcessPayments", "cst - Batch Process Payments - Detail - Integration - PLI")));
				JSONArray pmtLnItemsItemrecordData = pmtLnItemsParsing.createQueryJsonArray();
				log.info(this.getClass()+" Step #1 " + pmtLnItemsItemrecordData.length());
				new PaymentOutboundProcess().process(tws, writer, invoiceHeaderRecordData, pmtLnItemsItemrecordData);
    		}catch(Exception e ){
    			log.info(this.getClass()+" >>>>>>>>>>cstIntergrationConfig query error ");
    			e.printStackTrace();
    		}

        	
        	record(integration,FileName,FileLocation,tws);
        	//process(tws);
        }catch(Exception e){
        	e.printStackTrace();
        }

		return true;
	}

	public void record(String intName,String FileName,String FileLocation,TririgaWS tws){
		
		try{
	    	IntegrationField interfaceName = new IntegrationField();
			interfaceName.setName("cstNameTX");
			interfaceName.setValue(intName);
			  
			IntegrationField triFilenameTX = new IntegrationField();
			triFilenameTX.setName("triFilenameTX");
			triFilenameTX.setValue(FileName);
	
			
	    	IntegrationField Status = new IntegrationField();
	    	Status.setName("Status");
	    	Status.setValue("Extract Generated");      	
	    	        	
	    	IntegrationField filePath = new IntegrationField();
	    	filePath.setName("cstFilePath");
	    	filePath.setValue(FileLocation);
	
	        IntegrationField[] generalInfoFields = new IntegrationField[] { interfaceName,triFilenameTX,Status,filePath};
	    	IntegrationSection generalInfo = new IntegrationSection();
	
	    	generalInfo.setName("General");
	    	generalInfo.setFields(generalInfoFields);
	    	IntegrationSection[] sections = new IntegrationSection[] {
	    			generalInfo
	    	};
	    	IntegrationRecord integrationRecord = new IntegrationRecord();
	        integrationRecord.setId(-1l);
	        integrationRecord.setSections(sections);
	             
	        integrationRecord.setActionName("triCreate");
	        integrationRecord.setModuleId(tws.getModuleId("Data Utilities"));
	        
	        log.info ("ModuleID"+tws.getModuleId("triIntermediate"));
	        log.info ("setObjectTypeId"+tws.getObjectTypeId("Data Utilities", "cstBatchProcessDTO"));
	            
	        integrationRecord.setObjectTypeId(tws.getObjectTypeId("Data Utilities", "cstBatchProcessDTO"));
	        integrationRecord.setObjectTypeName("cstBatchProcessDTO");
	        ResponseHelperHeader responseHelperHeader =  tws.saveRecord( new IntegrationRecord[] { integrationRecord });
	        ResponseHelper[] responseHelper = responseHelperHeader.getResponseHelpers();
	        log.info (this.getClass()+">getStatus "+responseHelper[0].getStatus());
			if(responseHelper[0].getStatus().equalsIgnoreCase("Successful")){
					log.info (this.getClass()+">>>>>>>>>>Call Successfull");
	
		     }
        }catch(Exception e){
        	log.info (this.getClass()+">>>>>>>>>>Call Failed");
			e.printStackTrace();
		}
	}

}

