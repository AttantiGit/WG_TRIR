package com.tririga.custom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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

public class PeopleInbound implements CustomBusinessConnectTask{
	// This was developed for Walgreens I Lease Implementation in May 2018. The Batch process payments including the RE invoices (reports are filtered on Issued status and for the Process Payments issued on a daily basis)
	// The sum of all the paymentline items for the corresponding lease is created as a new row into cstBatchHeaderDTO using a custom workflow
	// The file generation process is based on a scheduler generated and the file is "~" separator.
	
	
	private Logger log = Logger.getLogger(this.getClass());	
	@Override
	public boolean execute(TririgaWS tws, long userId, Record[] records) {
		log.info(this.getClass()+" >>>>>>>>>>Code Executing inside Custom Task ");

        try{
    		String integration = "HRIS People Inbound";
    		String FileName = null;
    		String FileLocation = "";
        	tws.register(userId);
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
    					//FileName=FileName+"."+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    					String IntegrationType = configRecord.getString("IntegrationType");
    					FileLocation = configRecord.getString("FileLocation");
        				log.info(this.getClass()+"IntegrationName = "+IntegrationName);
    					log.info(this.getClass()+"FileName = "+FileName);
    					log.info(this.getClass()+"IntegrationType = "+IntegrationType);
    					log.info(this.getClass()+"FileLocation = "+FileLocation);

    					String csvFile = FileLocation+"/"+FileName;
    					log.info(this.getClass()+"csvFile = "+csvFile);
   					    File file = new File(csvFile);	
    					BufferedReader br = new BufferedReader(new FileReader(file));
    					List<IntegrationField[]> data = new PeopleInboundProcess().process(br);
    					Iterator<IntegrationField[]> itr  = data.iterator();
    					while(itr.hasNext()){    						
    						IntegrationField[] generalInfoFieldsPayment = (IntegrationField[])itr.next();
					        log.info (" Begin cstHRISPeopleDTO ");
				        	IntegrationSection generalInfoPayment = new IntegrationSection();

				        	generalInfoPayment.setName("General");
				        	generalInfoPayment.setFields(generalInfoFieldsPayment);
				        	IntegrationSection[] sectionsPaymentInboundDTO = new IntegrationSection[] {
				        			generalInfoPayment
				        	};
				        	log.info (" Before Sections of cstHRISPeopleDTO ");
				        	
				        	IntegrationRecord integrationRecordHRISPeople = new IntegrationRecord();
				        	integrationRecordHRISPeople.setId(-1l);
				        	integrationRecordHRISPeople.setSections(sectionsPaymentInboundDTO);
					             
				        	 log.info (" Before cstDraft of cstHRISPeopleDTO ");
				        	 integrationRecordHRISPeople.setActionName("cstDraft");
				        	 integrationRecordHRISPeople.setModuleId(tws.getModuleId("cstIntegration"));
					        
				           // log.info ("ModuleID"+tws.getModuleId("triIntermediate"));
					        log.info ("setObjectTypeId cstHRISPeopleDTO ="+tws.getObjectTypeId("cstIntegration", "cstHRISPeopleDTO"));
					            
					        integrationRecordHRISPeople.setObjectTypeId(tws.getObjectTypeId("cstIntegration", "cstHRISPeopleDTO"));
					        integrationRecordHRISPeople.setObjectTypeName("cstHRISPeopleDTO");
					        
					        try {
								
						        ResponseHelperHeader responseHelperHeaderPayment =  tws.saveRecord( new IntegrationRecord[] { integrationRecordHRISPeople });
						        ResponseHelper[] responseHelperPayment = responseHelperHeaderPayment.getResponseHelpers();
						        log.info (this.getClass()+">getStatus "+responseHelperPayment[0].getStatus());
								if(responseHelperPayment[0].getStatus().equalsIgnoreCase("Successful"))
								{
									log.info (this.getClass()+">>>>>>>>>> cstHRISPeopleDTO Call Successfull");
						        }
						        }catch(Exception e){
						        	log.info (this.getClass()+">>>>>>>>>> cstHRISPeopleDTO Call Failed");
									e.printStackTrace();
								}
    					}
    					br.close();
    				}
    			}

    		}catch(Exception e ){
    			log.info(this.getClass()+" >>>>>>>>>>cstIntergrationConfig query error ");
    			e.printStackTrace();
    		}

        	
        	record(integration,FileName,FileLocation,tws);
        	moveFile(FileLocation,FileName);
        	//process(tws);
        }catch(Exception e){
        	e.printStackTrace();
        }

		return true;
	}
	private void moveFile(String filePath,String fileName){
		log.info(this.getClass()+" >>>>>>>>>>moveFile ");
    	 String newFileName = getFileName(fileName);
    	 String newFolderName = "processed";
    	 log.info(this.getClass()+" >>>>>>>>>>moveFileFile.separator  "+File.separator); 
    	 log.info(this.getClass()+" >>>>>>>>>>newFileName "+newFileName); 
		// TODO Auto-generated method stub
		try{
			log.info(newFileName); 
			log.info("FILE CHECKING"+filePath+File.separator+fileName); 
	    	File file =new File(filePath+File.separator+fileName);
	    	if (file.exists()) {
	    		File directory = new File(filePath+File.separator+newFolderName);
	    		if (!directory.exists()){
	    			 if (directory.mkdir()){ System.out.println("Directory is created!");}
	    		}
   	    	   if(file.renameTo(new File(filePath+File.separator+newFolderName+File.separator+newFileName))){
   	    		log.info("File is moved successful!");
		       }else{
		    		System.out.println("File is failed to move!");
		       }

	    	}else{
	    		log.info("File doesn't exist");
	    	}
			
		}catch(Exception e){
			//System.out.println(e.printStackTrace());
			
			log.info("File is moved successful!");
			e.printStackTrace();
		}
	}
	 private String getFileName(String fileName)
	  {
			SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("_yyyyMMdd_hh.mm.ss.a");
		    int index = fileName.lastIndexOf(".");
		    if (index > -1)
		    {
		      String pre = fileName.substring(0, index);
		      String post = fileName.substring(index);
		      return pre + "_" + DATE_TIME_FORMAT.format(new Date(System.currentTimeMillis())) + post;
		    }
		    return fileName += DATE_TIME_FORMAT.format(new Date(System.currentTimeMillis()));
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
	
	    	generalInfo.setName("Detail");
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

