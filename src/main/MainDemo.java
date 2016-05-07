 
package main;
 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @problem : ORM Mapping
 * @author  : Almgwary
 * @date    : 7-Apr-2016
 * @thin    : --M
 **/
public class MainDemo {

	 
	public static void main(String[] args) {
		 Scanner in = new Scanner(System.in);
		
		 String fileContent ="-- NOTHINING";
		 try {fileContent = new String(Files.readAllBytes(Paths.get("code.txt")));} catch (IOException e) {System.err.println("ERROR OPPININNG FILE");}
		
		 // get model meta data
		 ArrayList<Model> modelsMetaData = StateMapping.run(fileContent);
		 // genrate assocation and inhertance groub
		 ClassesPrepration.run(modelsMetaData);
		 
		// System.out.println(modelsMetaData.toString());
		 // genrate SQl Code for each Type
		 SQL_Genrator.run(modelsMetaData);
		 
		 for(Model m : modelsMetaData ){
			 
			 System.out.println(m.getSQLCode_inhertanceType1());
		 
			 System.out.println(m.getSQLCode_inhertanceType2());
			  
			 System.out.println(m.getSQLCode_inhertanceType3());
			 
		 }
		 
		 
	}

}
