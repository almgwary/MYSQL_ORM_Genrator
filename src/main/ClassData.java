package main;

import java.util.ArrayList;

import javafx.util.Pair;

/**
 * @problem : ORM_Mapper
 * @author  : Almgwary
 * @date    : Apr 7, 2016
 * @thin    : --M
 **/
public class ClassData {

	private String name; 
	private ArrayList<Pair<String, String>> attributes = new ArrayList<Pair<String,String>>();
	
	private  Pair<String, String> Pk = null;
	
	
	 
	

	 
	 

	/**
	 * @return the pk
	 */
	public Pair<String, String> getPk() {
		return Pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(Pair<String, String> pk) {
		Pk = pk;
	}

	private String parent ;
	
	public ClassData( ) {
		
		
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the attributes
	 */
	public ArrayList<Pair<String, String>> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(ArrayList<Pair<String, String>> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return    "+-------------------------+\n"
				+ "+     Class:"+ name + "\n"
				+ "+-------------------------+\n"
				+ (parent != null ? ""
						+ "| Extends > " + (parent+"\n") : "") 
						
				+ (Pk != null ? ""
						+ "| Pk   " + (Pk+"\n") : "") 
						
				 
				+ (attributes != null ? "| Attributes:" + attributesToString() + " " : "")
				+  "\n+-------------------------+\n\n\n";
	}
	
	String attributesToString(){
		String s = "";
		for (Pair<String, String> a : attributes){
			s+="\n|  "+a.getKey()+" : "+a.getValue();
		}
		
		return s ;
	}
	
	
	
	
	
}
