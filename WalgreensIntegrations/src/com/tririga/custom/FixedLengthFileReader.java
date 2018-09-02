package com.tririga.custom;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
 
public class FixedLengthFileReader {

    public static List<Map<String, String>> readFixedLengthFile(String inputFile, String propertyFile) throws Exception {
             
        // Create subsequent File and FileInputStream objects from the input
        // data file
    	List<Map<String, String>> arrayList = new ArrayList<Map<String, String>>(); 
        File file = new File(inputFile);
        FileInputStream fstream = new FileInputStream(inputFile);
        // Create a Property object reading the XML meta data file containing the
        // lower and upper boundary character positions of each field
        Properties property = getProperty(propertyFile);
        //Enumeration em = property.keys();
            
        if (file.exists()) {
            //System.out.println("File already exists: file is -> " + file.toString());
                 
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
             
            // Read Input Data File Line By Line
            while ((strLine = br.readLine()) != null)   {
          
               if(!strLine.startsWith("T")){
               Map<String, String> hm = new HashMap<String, String>();
               // Create the Enumeration object with the property key values
               Enumeration<Object> em = property.keys();
               // Using the property object read and parse each line of the input file
               while(em.hasMoreElements()){
                    String str = (String)em.nextElement();
                   
                    // By splitting the string separated by comma we would get the upper and lower
                    // boundary of the field value
                    String[] strArray = property.getProperty(str).split(",");
                    
                    hm.put(str, strLine.substring(Integer.parseInt(strArray[0]), Integer.parseInt(strArray[1])));
               }
               
               arrayList.add(hm);
               }
       
            }
            // Close the input stream
            in.close(); 
        }
        
        return arrayList;
    }
         
    private static Properties getProperty(String fileName) throws Exception {
              
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream(fileName);
            prop.loadFromXML(fis);
            return prop;
     }
}