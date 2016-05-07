package main;

import javafx.util.Pair;
import jregex.Pattern;

/**
 * @problem : ORM_Mapper
 * @author  : Almgwary
 * @date    : Apr 14, 2016
 * @thin    : --M
 **/



 
//This Class contain all REGEXE * 
public class Pattrens {
	
	 // get first model till find  beging of second model or end of string
	 public static  Pattern r_getmodel = new Pattern("model ((\\s|\\n)|.)*");
	 // get model name
	 public static  Pattern r_getModelName = new Pattern("(model ).*");	
	 // get comment line
	 public static  Pattern r_getCommentLine = new Pattern("\\n--(.*)((\\s|\\n))");		 
	 // get all class
	 public static  Pattern r_getAllCalsses = new Pattern("(class ((.|(\\s|\\n))*?)(((\\s|\\n)|\\b)end))");	
	 // get class name 
	 public static  Pattern r_getClassName = new Pattern("class ((.)*?)(?=((\\s|\\n)| ))");	
	 //get parent class name if exist
	 public static Pattern r_getParrentClassName = new Pattern("(< )(([A-Za-z0-9]*?))((\\s|\\n)| )");
	 // get class attributes (name : type)
	 public static  Pattern r_getCalssAttributes = new Pattern("([A-Za-z0-9])* : ([A-Za-z0-9])*"); 
	 // get class attributes (name )
	 public static Pattern r_getAttributeName = new Pattern("([A-Za-z0-9]*?)(?= : )"); 
	 // get class attributes (type)
	 public static Pattern r_getAttributeType = new Pattern(" : (([A-Za-z0-9]*?))((\\s|\\n)| |$)"); 
	 // get association
	 public static Pattern r_getAssocaition = new Pattern("(association (.*?) between(\\s|\\n))(.|[(\\s|\\n)])*?end");
	 //  get association name
	 public static Pattern r_getAssocitaionName = new Pattern("(association )(.*?)(?= between)");
	 // get :  Employee[*]
	 public static Pattern r_getAssocitaionRelation = new Pattern(".*\\[.*\\]");
	 // get : [*]
	 public static Pattern r_getAssocitaionRelationRule = new Pattern("\\[.*\\]");
	 

	 public static Pair<String, String> uniAssocaition = new Pair<String, String>("1", "1");

}
