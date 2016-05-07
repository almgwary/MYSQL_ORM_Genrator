package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.util.Pair;

/**
 * @problem : ORM_Mapper
 * @author  : Almgwary
 * @date    : Apr 7, 2016
 * @thin    : --M
 **/

public class Model {
	 

	String name ;
	ArrayList<ClassData> myClasses = new ArrayList<ClassData>();
	ArrayList<Association> myAssociation = new ArrayList<Association>();
	// className AttributeName class that have the PK 
	private ArrayList<Pair<String, Pair <String, String>>> Fk = new ArrayList<Pair<String,Pair<String,String>>>();
		
	// collection classes of same parent in Map 
		 
	private HashMap<ClassData, ArrayList<ClassData>> inheritanceGroups = new HashMap<ClassData, ArrayList<ClassData>>() ;
	
	// single table
	private ArrayList<ClassData> inhertanceType1 = new ArrayList<ClassData>();
	private ArrayList<Pair<String, Pair <String, String>>> Fk_inhertanceType1 = new ArrayList<Pair<String,Pair<String,String>>>();
	
	// concreate calsses
	private ArrayList<ClassData> inhertanceType2 = new ArrayList<ClassData>();
	private ArrayList<Pair<String, Pair <String, String>>> Fk_inhertanceType2 = new ArrayList<Pair<String,Pair<String,String>>>();
	
	// calss per table
	private ArrayList<ClassData> inhertanceType3 = new ArrayList<ClassData>();
	private ArrayList<Pair<String, Pair <String, String>>> Fk_inhertanceType3 = new ArrayList<Pair<String,Pair<String,String>>>();
	
	// this classes are dublicat and will be removed
	private HashSet<ClassData> dirtyClasses = new HashSet<ClassData>()  ;
	
	
	// this FK are duplicate and will be removed
	private HashSet<Pair<String, Pair <String, String>>> dirtyFk =  new HashSet<Pair<String,Pair<String,String>>>();
	
	
	
	
	
	// add updated tables names 
	// lastName , newName
	private HashMap<String, String> updatedTablesnames = new HashMap<String, String>();
	
	
	
	
	
	
	
	String SQLCode_inhertanceType1 = "" ; 
	String SQLCode_inhertanceType2 = "" ; 
	String SQLCode_inhertanceType3 = "" ; 
	
	
	
	
	
	
	/**
	 * @return the sQLCode_inhertanceType1
	 */
	public String getSQLCode_inhertanceType1() {
		return SQLCode_inhertanceType1;
	}

	/**
	 * @param sQLCode_inhertanceType1 the sQLCode_inhertanceType1 to set
	 */
	public void setSQLCode_inhertanceType1(String sQLCode_inhertanceType1) {
		SQLCode_inhertanceType1 = sQLCode_inhertanceType1;
	}

	/**
	 * @return the sQLCode_inhertanceType2
	 */
	public String getSQLCode_inhertanceType2() {
		return SQLCode_inhertanceType2;
	}

	/**
	 * @param sQLCode_inhertanceType2 the sQLCode_inhertanceType2 to set
	 */
	public void setSQLCode_inhertanceType2(String sQLCode_inhertanceType2) {
		SQLCode_inhertanceType2 = sQLCode_inhertanceType2;
	}

	/**
	 * @return the sQLCode_inhertanceType3
	 */
	public String getSQLCode_inhertanceType3() {
		return SQLCode_inhertanceType3;
	}

	/**
	 * @param sQLCode_inhertanceType3 the sQLCode_inhertanceType3 to set
	 */
	public void setSQLCode_inhertanceType3(String sQLCode_inhertanceType3) {
		SQLCode_inhertanceType3 = sQLCode_inhertanceType3;
	}

	/**
	 * @return the updatedTablesnames
	 */
	public HashMap<String, String> getUpdatedTablesnames() {
		return updatedTablesnames;
	}

	/**
	 * @param updatedTablesnames the updatedTablesnames to set
	 */
	public void setUpdatedTablesnames(HashMap<String, String> updatedTablesnames) {
		this.updatedTablesnames = updatedTablesnames;
	}

	/**
	 * @return the dirtyClasses
	 */
	public HashSet<ClassData> getDirtyClasses() {
		return dirtyClasses;
	}

	/**
	 * @param dirtyClasses the dirtyClasses to set
	 */
	public void setDirtyClasses(HashSet<ClassData> dirtyClasses) {
		this.dirtyClasses = dirtyClasses;
	}

	/**
	 * @return the dirtyFk
	 */
	public HashSet<Pair<String, Pair<String, String>>> getDirtyFk() {
		return dirtyFk;
	}

	/**
	 * @param dirtyFk the dirtyFk to set
	 */
	public void setDirtyFk(HashSet<Pair<String, Pair<String, String>>> dirtyFk) {
		this.dirtyFk = dirtyFk;
	}

	/**
	 * @return the fk_inhertanceType1
	 */
	public ArrayList<Pair<String, Pair<String, String>>> getFk_inhertanceType1() {
		return Fk_inhertanceType1;
	}

	/**
	 * @param fk_inhertanceType1 the fk_inhertanceType1 to set
	 */
	public void setFk_inhertanceType1(ArrayList<Pair<String, Pair<String, String>>> fk_inhertanceType1) {
		Fk_inhertanceType1 = fk_inhertanceType1;
	}

	/**
	 * @return the fk_inhertanceType2
	 */
	public ArrayList<Pair<String, Pair<String, String>>> getFk_inhertanceType2() {
		return Fk_inhertanceType2;
	}

	/**
	 * @param fk_inhertanceType2 the fk_inhertanceType2 to set
	 */
	public void setFk_inhertanceType2(ArrayList<Pair<String, Pair<String, String>>> fk_inhertanceType2) {
		Fk_inhertanceType2 = fk_inhertanceType2;
	}

	/**
	 * @return the fk_inhertanceType3
	 */
	public ArrayList<Pair<String, Pair<String, String>>> getFk_inhertanceType3() {
		return Fk_inhertanceType3;
	}

	/**
	 * @param fk_inhertanceType3 the fk_inhertanceType3 to set
	 */
	public void setFk_inhertanceType3(ArrayList<Pair<String, Pair<String, String>>> fk_inhertanceType3) {
		Fk_inhertanceType3 = fk_inhertanceType3;
	}

	/**
	 * @return the inhertanceType1
	 */
	public ArrayList<ClassData> getInhertanceType1() {
		return inhertanceType1;
	}

	/**
	 * @param inhertanceType1 the inhertanceType1 to set
	 */
	public void setInhertanceType1(ArrayList<ClassData> inhertanceType1) {
		this.inhertanceType1 = inhertanceType1;
	}

	/**
	 * @return the inhertanceType2
	 */
	public ArrayList<ClassData> getInhertanceType2() {
		return inhertanceType2;
	}

	/**
	 * @param inhertanceType2 the inhertanceType2 to set
	 */
	public void setInhertanceType2(ArrayList<ClassData> inhertanceType2) {
		this.inhertanceType2 = inhertanceType2;
	}

	/**
	 * @return the inhertanceType3
	 */
	public ArrayList<ClassData> getInhertanceType3() {
		return inhertanceType3;
	}

	/**
	 * @param inhertanceType3 the inhertanceType3 to set
	 */
	public void setInhertanceType3(ArrayList<ClassData> inhertanceType3) {
		this.inhertanceType3 = inhertanceType3;
	}

	/**
	 * @return the fk
	 */
	public ArrayList<Pair<String, Pair<String, String>>> getFk() {
		return Fk;
	}

	/**
	 * @param fk the fk to set
	 */
	public void setFk(ArrayList<Pair<String, Pair<String, String>>> fk) {
		Fk = fk;
	}

	public Model( ) {
		super();
 
	}

	 
 

	 
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Model [" + (name != null ? "name=" + name + ", " : "")
				+ (myClasses != null ? "myClasses=" + myClasses + ", " : "")
				+ (myAssociation != null ? "myAssociation=" + myAssociation + ", " : "")
				+ (Fk != null ? "Fk=" + Fk + ", " : "")
				+ (inheritanceGroups != null ? "inheritanceGroups=" + inheritanceGroups + ", " : "")
				+ (inhertanceType1 != null ? "inhertanceType1=" + inhertanceType1 + ", " : "")
				+ (Fk_inhertanceType1 != null ? "Fk_inhertanceType1=" + Fk_inhertanceType1 + ", " : "")
				+ (inhertanceType2 != null ? "inhertanceType2=" + inhertanceType2 + ", " : "")
				+ (Fk_inhertanceType2 != null ? "Fk_inhertanceType2=" + Fk_inhertanceType2 + ", " : "")
				+ (inhertanceType3 != null ? "inhertanceType3=" + inhertanceType3 + ", " : "")
				+ (Fk_inhertanceType3 != null ? "Fk_inhertanceType3=" + Fk_inhertanceType3 + ", " : "")
				+ (dirtyClasses != null ? "dirtyClasses=" + dirtyClasses + ", " : "")
				+ (dirtyFk != null ? "dirtyFk=" + dirtyFk + ", " : "")
				+ (updatedTablesnames != null ? "updatedTablesnames=" + updatedTablesnames + ", " : "")
				+ (SQLCode_inhertanceType1 != null ? "SQLCode_inhertanceType1=" + SQLCode_inhertanceType1 + ", " : "")
				+ (SQLCode_inhertanceType2 != null ? "SQLCode_inhertanceType2=" + SQLCode_inhertanceType2 + ", " : "")
				+ (SQLCode_inhertanceType3 != null ? "SQLCode_inhertanceType3=" + SQLCode_inhertanceType3 : "") + "]";
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
	 * @return the myClasses
	 */
	public ArrayList<ClassData> getMyClasses() {
		return myClasses;
	}

	/**
	 * @param myClasses the myClasses to set
	 */
	public void setMyClasses(ArrayList<ClassData> myClasses) {
		this.myClasses = myClasses;
	}

	/**
	 * @return the myAssociation
	 */
	public ArrayList<Association> getMyAssociation() {
		return myAssociation;
	}

	/**
	 * @param myAssociation the myAssociation to set
	 */
	public void setMyAssociation(ArrayList<Association> myAssociation) {
		this.myAssociation = myAssociation;
	}

	public HashMap<ClassData, ArrayList<ClassData>> getInheritanceGroups() {
		return inheritanceGroups;
	}

	public void setInheritanceGroups(HashMap<ClassData, ArrayList<ClassData>> inheritanceGroups) {
		this.inheritanceGroups = inheritanceGroups;
	} 
	
	 
	
}
