package com.tririga.custom;

import java.io.BufferedReader;
import java.util.*;

import org.apache.log4j.Logger;
import com.tririga.ws.TririgaWS;
import com.tririga.ws.dto.IntegrationField;
import com.tririga.custom.CSVUtils;

public class MasterLookUpInboundProcess {
	private Logger log = Logger.getLogger(this.getClass());	
	public List < IntegrationField[]> process(TririgaWS tws,BufferedReader br ){
		log.info(this.getClass()+"You are in OrganizationInboundProcess ");
		List < IntegrationField[]> dataRows = new ArrayList<>();
		try{
			  String st;
			  
			  while ((st = br.readLine()) != null){
				  System.out.println("*****************************************");
				  st=st.replaceAll("\uFFFD","|");  

				  List<String> line = CSVUtils.parseLine(st,'|');
				  System.out.println(line);
				  
				  System.out.println("business_unit_id --> "+line.get(0));
				  System.out.println("effective_date --> "+line.get(1));
				  System.out.println("expiration_date --> "+line.get(2));
				  System.out.println("business_unit_name --> "+line.get(3));
				  System.out.println("business_unit_type --> "+line.get(4));
				  System.out.println("business_unit_level --> "+line.get(5));
				  System.out.println("abbreviated_name --> "+line.get(6));
				  System.out.println("business_unit_code --> "+line.get(7));
				  System.out.println("within --> "+line.get(8));
				  System.out.println("create_userid --> "+line.get(9));
				  System.out.println("create_dttm --> "+line.get(10));
				  System.out.println("update_userid --> "+line.get(11));
				  System.out.println("cstAccountIDTX --> "+line.get(12));
				  
				  
				  System.out.println("original_effective_date --> "+line.get(13));
				  System.out.println("position_cleanse_ind --> "+line.get(14));
				  
				  IntegrationField interfaceName = new IntegrationField();
				  interfaceName.setName("cstAccountIDTX");
				  interfaceName.setValue(line.get(12));
		    		  		
//				  IntegrationField triFilenameTX = new IntegrationField();
//				  triFilenameTX.setName("triFilenameTX");
//				  //triFilenameTX.setValue(FileName);
//
//				  IntegrationField Status = new IntegrationField();
//				  Status.setName("Status");
//				  Status.setValue("Updated Voucher");      	
//		        	        	
//				  IntegrationField filePath = new IntegrationField();
//				  filePath.setName("cstFilePath");
//				  //filePath.setValue(FileLocation);
		        	
		          log.info ("Begin cstBatchProcessDTO ");
		            
		         // IntegrationField[] generalInfoFields = new IntegrationField[] {interfaceName,triFilenameTX,Status,filePath};
		          IntegrationField[] generalInfoFields = new IntegrationField[] {interfaceName};
		          dataRows.add(generalInfoFields);
			  }
			  
		}catch (Exception e){
			e.printStackTrace();
		}	
		log.info("No Of rows"+dataRows.size());
		return dataRows;
	}
}
