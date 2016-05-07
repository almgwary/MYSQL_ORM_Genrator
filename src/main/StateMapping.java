package main;

import java.util.ArrayList;

import javafx.util.Pair;
import jregex.Matcher;
import jregex.Pattern;


/**
 * @problem : ORM_Mapper
 * @author  : Almgwary
 * @date    : Apr 7, 2016
 * @thin    : --M
 **/


/**
 * Provide sequence of tokens and this class will provide valid mapping 
 * 
 * be_SURE not sending any comment 
 * */
public class StateMapping {
	
	
	// IF file contain more than one model will provide  model for each one
	private  static  ArrayList<Model> models = new ArrayList<Model>();
	 // current working model
	 private static  int currentModelIndex = -1;
	 
	 
	 
	 
	 
	 
	 public static ArrayList<Model> run (String buffer){
 		 Matcher modelMatcher = Pattrens.r_getmodel.matcher(buffer);
		 while(modelMatcher.find()){
			 Model model = new Model();
			 String modelBuffer = modelMatcher.toString() ;
			 

			 String modelName = getModelname(modelBuffer);
			 model.setName(modelName);
			 System.out.println("NewModel$ ModelName:"+modelName);
			
			 
			 System.out.print("\nRemovingComments$");
			 modelBuffer = removeComments(modelBuffer);
			  
			  System.out.print("\n\nCollectingClasses$ ");
			  ArrayList<String> classes = getClasses(modelBuffer);
			  System.out.println("count = "+classes.size());
			  
			  
			  System.out.print("CollectingAssociations$");
			  ArrayList<String> associations = getAssociation(modelBuffer);
			  System.out.println("count = "+associations.size()+"");
			  
			  // creating Classes from classes buffer and add to model 
			  for (String c : classes) {
				 model.getMyClasses().add(createClass(c));
			  }
			  
			  // creating associations from association buffer and add to model 
			  for(String as : associations){
				   
				  model.getMyAssociation().add(createAssociation(as));
			  }
			  
			  models.add(model);
			  currentModelIndex++;
			  
		   }
		
		 System.out.println("StateMapping$ FINISHED\n\n\n");
		 
		 return models;
	 }
	 
	 // remove all comments comment must start at new line becouse of [1--*] is not a comment
	 private static String removeComments(String buffer){
		 
		 Matcher comments = Pattrens.r_getCommentLine.matcher(buffer);
		 while(comments.find()){
			 String comment = comments.toString();
			System.out.print(comment);
			 buffer = buffer.replace(comment, "\n");
		 }
		 
		 return buffer ;
	 }
	 
	// collecting classes
	private static ArrayList<String> getClasses(String buffer){
			 ArrayList<String> classes = new ArrayList<String>();
			 Matcher classMatcher =  Pattrens.r_getAllCalsses.matcher(buffer);
		      while(classMatcher.find()){
		    	  String class_  = classMatcher.toString();
		    	  classes.add(class_);
		      }
			 return classes;
		 }
		 
 	 
	// Collecting association
	private static ArrayList<String> getAssociation(String buffer){
			 ArrayList<String> associations = new ArrayList<String>();
			 Matcher associationMatcher =  Pattrens.r_getAssocaition.matcher(buffer);
		      while(associationMatcher.find()){
		    	  String class_  = associationMatcher.toString();
		    	  associations.add(class_);
		    	  //System.err.println(class_);
		      }
			 return associations;
		 }

	// create classes and attributes
	private static ClassData createClass (String classString){
		
		ClassData c = new ClassData();
		// get class name
		Matcher name = Pattrens.r_getClassName.matcher(classString);
		name.find();
		// removing Positive behind  because it no supported in language
		String s = name.toString().replace("class ", "");
		
		c.setName(s);
		// get class parent
		Matcher parent = Pattrens.r_getParrentClassName.matcher(classString);
		if(parent.find()) {
			// removing Positive behind  because it no supported in language
			s = parent.toString().replace("< ", "");
			c.setParent(s);
		}
		// createID
		c.setPk(new Pair<String, String>("ID", "String"));
		// get class attributes
		Matcher attributes = Pattrens.r_getCalssAttributes.matcher(classString);
		while (attributes.find()) {
			
			String attribute = attributes.toString();
			//System.out.println(attribute);
			Matcher attributeName = Pattrens.r_getAttributeName.matcher(attribute);
			attributeName.find();
			
			Matcher attributeType = Pattrens.r_getAttributeType.matcher(attribute);
			attributeType.find();
			s= attributeType.toString().replace(" : ", "");
			
			c.getAttributes().add(new Pair<String, String>(attributeName.toString(), s));
		}
		
		//System.out.println(c.toString());
		
		
		
		return c ;
	}

	// get model name
	private  static String getModelname(String modelBuffer){
		String s = "";
		
		Matcher m =  Pattrens.r_getModelName.matcher(modelBuffer);
		m.find();
		s = m.toString().replace("model ", "");
		return s;
	}


	private static Association createAssociation(String associationBuffer){
		Association a = new Association();
		String name = "";
		Pair<String, Pair<String, String>> fst ;
		Pair<String, Pair<String, String>> snd ;
		
		// getting name
		Matcher n = Pattrens.r_getAssocitaionName.matcher(associationBuffer);
		n.find();
		name= n.toString().replace("association ", "");
		
		
		Matcher relations = Pattrens.r_getAssocitaionRelation.matcher(associationBuffer);
		Matcher rules = Pattrens.r_getAssocitaionRelationRule.matcher(associationBuffer);
		
		
		// getting first relation
		relations.find();
		rules.find();
		String r1 = relations.toString();
		// getting rule
		String r1Rule = rules.toString();
		//removng rule from relation to get name
		r1=r1.replace(r1Rule, "");
		
		
		//System.out.println(r1+"  --  "+getAssociationMinMaxRule(r1Rule).getKey() +" ::  "+getAssociationMinMaxRule(r1Rule).getValue() );
		
		
		// getting second relation
		relations.find();
		rules.find();
		String r2 = relations.toString();
		// getting rule
		String r2Rule = rules.toString();
		//removng rule from relation to get name
		r2=r2.replace(r2Rule, "");
		
		//System.out.println(r2+"  --  "+getAssociationMinMaxRule(r2Rule).getKey() +" ::  "+getAssociationMinMaxRule(r2Rule).getValue() );
		
		
		fst= new Pair<String, Pair<String,String>>(r1,getAssociationMinMaxRule(r1Rule));
		snd= new Pair<String, Pair<String,String>>(r2, getAssociationMinMaxRule(r2Rule));
		   
		a.setName(name);
		a.setFirstRules(fst);
		a.setSecondtRules(snd);
		
		
		//System.out.println(a.toString());
		return a ;
	}
	
	
	private static Pair<String, String> getAssociationMinMaxRule(String ruleBuffer){
		String r1 ="" ,r2="" ;
		int bufferLength =ruleBuffer.length() ;
		ruleBuffer = ruleBuffer.replace("[", "");
		ruleBuffer = ruleBuffer.replace("]", "");
		// rule like [x]
		if(bufferLength==3){
			
			r2 = ruleBuffer;
			if(ruleBuffer.equals("*")){
				r1="0";
			}else{r1 = r2 ;}
			
		}else{
			int splitIndex  = ruleBuffer.indexOf("--");
			
			// if not found thow Exception
			if(splitIndex<0)try {throw new Exception() ;} catch (Exception e) {System.err.println("Association not Valid");}
			
			r2 = ruleBuffer.substring(splitIndex+2);
			r1 = ruleBuffer.replace(r2,"").replace("--", "");
			
			
			
		}
		 
		return new Pair<String, String>(r1, r2);
	}
	
}
