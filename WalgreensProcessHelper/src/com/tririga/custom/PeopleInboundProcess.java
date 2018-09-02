package com.tririga.custom;

import java.io.BufferedReader;
import java.util.*;

import org.apache.log4j.Logger;

import com.tririga.ws.dto.IntegrationField;
import com.tririga.custom.CSVUtils;

public class PeopleInboundProcess {
	private Logger log = Logger.getLogger(this.getClass());	
	public List < IntegrationField[]> process(BufferedReader br ){
		log.info(this.getClass()+"You are in PeopleInboundProcess ");
		List < IntegrationField[]> dataRows = new ArrayList<>();
		try{
			  String st;

			  while ((st = br.readLine()) != null){
				  log.info(this.getClass()+"*****************************************");
				  st=st.replaceAll("\uFFFD","|");  

				  List<String> line = CSVUtils.parseLine(st,'|');
				  log.info(this.getClass()+"--" +line);
				  
				  System.out.println("cstEmployeeIDTX --> "+line.get(0));
				  System.out.println("sed_employee --> "+line.get(1));
				  System.out.println("cstFirstNameTX --> "+line.get(2));
				  System.out.println("cstMiddleNameTX --> "+line.get(3));
				  System.out.println("cstLastNameTX --> "+line.get(4));
				  System.out.println("home_address_line_1 --> "+line.get(5));
				  System.out.println("home_address_line_2 --> "+line.get(6));
				  System.out.println("home_city --> "+line.get(7));
				  System.out.println("home_state --> "+line.get(8));
				  System.out.println("home_zip_code --> "+line.get(9));
				  System.out.println("home_country_code --> "+line.get(10));
				  System.out.println("home_country --> "+line.get(11));
				  System.out.println("gender --> "+line.get(12));
				  System.out.println("birth_date --> "+line.get(13));
				  System.out.println("primary_badge_number --> "+line.get(14));
				  System.out.println("race_code --> "+line.get(15));
				  System.out.println("status_code --> "+line.get(16));
				  System.out.println("language --> "+line.get(17));
				  System.out.println("alt_language_1 --> "+line.get(18));
				  System.out.println("alt_language_2 --> "+line.get(19));
				  System.out.println("region_number --> "+line.get(20));
				  System.out.println("district_number --> "+line.get(21));
				  System.out.println("cstLocationNumberNU --> "+line.get(22));
				  System.out.println("payroll_location_number --> "+line.get(23));
				  System.out.println("actual_payroll_location_number --> "+line.get(24));
				  System.out.println("location_type --> "+line.get(25));
				  System.out.println("position_code --> "+line.get(26));
				  System.out.println("cstJobTitileIDTX --> "+line.get(27));
				  System.out.println("oh_position_id --> "+line.get(28));
				  System.out.println("oh_position_state --> "+line.get(29));
				  System.out.println("mgr_position_id --> "+line.get(30));
				  System.out.println("cstMgrEmpIDTX --> "+line.get(31));
				  System.out.println("compensation_code --> "+line.get(32));
				  System.out.println("cstJobTitleTX --> "+line.get(33));
				  System.out.println("condensed_job_title --> "+line.get(34));
				  System.out.println("salary_program_id --> "+line.get(35));
				  System.out.println("salary_program_desc --> "+line.get(36));
				  System.out.println("grade_level --> "+line.get(37));
				  System.out.println("group_number --> "+line.get(38));
				  System.out.println("bonus_program --> "+line.get(39));
				  System.out.println("benefits_class --> "+line.get(40));
				  System.out.println("benefits_code --> "+line.get(41));
				  System.out.println("exempt_status --> "+line.get(42));
				  System.out.println("exempt_code --> "+line.get(43));
				  System.out.println("pos_unit_id --> "+line.get(44));
				  System.out.println("pos_unit_name --> "+line.get(45));
				  System.out.println("pos_unit_type_desc --> "+line.get(46));
				  System.out.println("within --> "+line.get(47));
				  System.out.println("cstDivisionNumberTX --> "+line.get(48));
				  System.out.println("cstDivisionNameTX --> "+line.get(50));
				  System.out.println("cstDepartmentNumberTX --> "+line.get(51));
				  System.out.println("cstDepartmentNameTX --> "+line.get(52));
				  System.out.println("sub_department_id --> "+line.get(53));
				  System.out.println("sub_name --> "+line.get(54));
				  System.out.println("sub_department_id_2 --> "+line.get(55));
				  System.out.println("sub2_name --> "+line.get(56));
				  System.out.println("eeo1_code --> "+line.get(57));
				  System.out.println("job_group --> "+line.get(58));
				  System.out.println("standard_occupation_code --> "+line.get(59));
				  System.out.println("census_occupation_code --> "+line.get(60));
				  System.out.println("mgr_first_name --> "+line.get(61));
				  System.out.println("mgr_middle_name --> "+line.get(62));
				  System.out.println("mgr_last_name --> "+line.get(63));
				  System.out.println("benefits_union_code --> "+line.get(64));
				  System.out.println("cstMailStopTX --> "+line.get(65));
				  System.out.println("work_city --> "+line.get(66));
				  System.out.println("state --> "+line.get(67));
				  System.out.println("time_zone --> "+line.get(68));
				  System.out.println("cstHireDateDA --> "+line.get(69));
				  System.out.println("adjusted_hire_date --> "+line.get(70));
				  System.out.println("location_start_date --> "+line.get(71));
				  System.out.println("position_start_date --> "+line.get(72));
				  System.out.println("pos_job_start_date --> "+line.get(73));
				  System.out.println("termination_code --> "+line.get(74));
				  System.out.println("cstTermDateDA --> "+line.get(75));
				  System.out.println("job_title_start_date --> "+line.get(76));
				  System.out.println("payroll_location_start_date --> "+line.get(77));
				  System.out.println("grade_start_date --> "+line.get(78));
				  System.out.println("group_start_date --> "+line.get(79));
				  System.out.println("pay_group --> "+line.get(80));
				  System.out.println("rate_type_ind --> "+line.get(81));
				  System.out.println("rate_freq_ind --> "+line.get(82));
				  System.out.println("format_employee_id --> "+line.get(83));
				  System.out.println("format_compensation_code --> "+line.get(84));
				  System.out.println("format_benefits_class --> "+line.get(85));
				  System.out.println("cstWorkEmail --> "+line.get(86));
				  System.out.println("cstWorkPhoneTX --> "+line.get(87));
				  System.out.println("cstIsMgrTX --> "+line.get(88));
				  System.out.println("row_number --> "+line.get(89));
				  System.out.println("level_number --> "+line.get(90));
				  System.out.println("expand_3 --> "+line.get(91));
				  System.out.println("cstPwlTypeTX --> "+line.get(92));
				  System.out.println("daylight_savings --> "+line.get(93));
				  System.out.println("pay_date --> "+line.get(94));
				  System.out.println("mgmt_level --> "+line.get(95));
				  System.out.println("cstBandIDTX --> "+line.get(96));
				  System.out.println("acq_ind --> "+line.get(97));
				  System.out.println("pay_ind --> "+line.get(98));
				  System.out.println("union_cd --> "+line.get(99));
				  System.out.println("expand_9 --> "+line.get(100));
				  System.out.println("expand_10 --> "+line.get(101));
				  System.out.println("area_number --> "+line.get(102));
				  
				  
				  System.out.println("original_effective_date --> "+line.get(13));
				  System.out.println("position_cleanse_ind --> "+line.get(14));
				  
				  IntegrationField interfaceName = new IntegrationField();
				  interfaceName.setName("cstBandIDTX");
				  interfaceName.setValue(line.get(96));
		    		  		
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
				  //filePath.setValue(FileLocation);
		        	
		          log.info ("Begin cstBatchProcessDTO ");
		            
		         // IntegrationField[] generalInfoFields = new IntegrationField[] {interfaceName,triFilenameTX,Status,filePath};
		          IntegrationField[] generalInfoFields = new IntegrationField[] {interfaceName};
		          dataRows.add(generalInfoFields);
			  }
			  
		}catch (Exception e){
			log.info(this.getClass()+"Exception");
			e.printStackTrace();
		}	
		log.info(this.getClass()+"No Of rows"+dataRows.size());
		return dataRows;
	}
}
