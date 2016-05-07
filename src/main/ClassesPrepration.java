package main;

import java.util.ArrayList;

import javafx.util.Pair;

 
/**
 * @problem : ORM_Mapper
 * @author  : Almgwary
 * @date    : Apr 14, 2016
 * @thin    : --M
 **/

/**
 *  1- Assign associations to classes #done
 *  2- Assign ForignKeys #done
 *  3- collect all groups of inheritance classes 
 *  		// adding classes to inheritance groups make it duplicate in model 
 *  4- map each group to the 3 types of inheritance with PrimaryKeys
 *  
 * */
public class ClassesPrepration {

	public static void run(ArrayList<Model> models){
		 
		for(Model m : models){
			
			// 1-Assign association to classes
			assignAssociation(m);
			System.out.println("ClassesPrepration.assignAssociation()$");
			
			// 2- Collect all groups of inheritance classes
			collectInheratnceClasses(m);
			System.out.println("ClassesPrepration.collectInheratnceClasses()$");
			
			// 3- Create  inheratnce types
			// reAssign FK for 3 FK grope  
			createInhertanceClasses(m);
			System.out.println("ClassesPrepration.createInhertanceClasses()$");
			
			
			// 4- make all dirty classes and FK = null
			deleteDirty(m);
			
			
			
			
		}
	}
	
	
	/**
	 * Assign association to classes 
	 * if need more classes they will be created
	 * assign foreign keys to model 
	 * */
	private static void assignAssociation(Model m){
		
		for(Association s : m.getMyAssociation()){
			// get  class name
			String class1Name = s.getFirstRules().getKey().substring(1);
			String class2Name = s.getSecondtRules().getKey().substring(1);
			
			
			// if association  [1--*]   just add foreign Key to [*] class , we have one foreign key
			 if(s.getFirstRules().getValue().equals(Pattrens.uniAssocaition)){
				
				
				
				// find it and add attribute
				 ClassData c  = getCalssByName(class2Name, m);
				// make new Attribute Name
				String attributeName  = class1Name+"_ID" ; 
				// add New Attribute
				c.getAttributes().add(new Pair<String, String>(attributeName, "String"));
				//System.err.println("E1"+c.toString());
				// add new ForignKey to model List
				m.getFk().add( getNewFK(class2Name,attributeName,class1Name));
					
				
				
			}
			// if association [*--1] just add foreign Key to [*] class , we have one foreign key
			else if(s.getSecondtRules().getValue().equals(Pattrens.uniAssocaition) ){
				 
				// find it and add attribute
				ClassData c  = getCalssByName(class1Name, m);
				// make new Attribute Name
				String attributeName  = class2Name+"_ID" ; 
				// add New Attribute
				c.getAttributes().add(new Pair<String, String>(attributeName, "String"));
				// add new ForignKey to model List
				m.getFk().add( getNewFK(class1Name,attributeName,class2Name));
						 
			}
			// create new class  , add two foreign key
			else{
				
				ClassData nC = new ClassData() ;
				nC.setName(s.getName());
				String attribute1Name  = class1Name+"_ID" ; 
				String attribute2Name  = class2Name+"_ID" ;
				nC.getAttributes().add(new Pair<String, String>(attribute1Name, "String"));
				nC.getAttributes().add(new Pair<String, String>(attribute2Name, "String"));
				m.getMyClasses().add(nC);
				
				m.getFk().add( getNewFK(nC.getName(),attribute1Name,class1Name));
				m.getFk().add( getNewFK(nC.getName(),attribute2Name,class2Name));
				
			}
			
			
		}
	}
	
	private static Pair<String, Pair<String, String>> getNewFK(String calssname, String AttrbuteName ,String calssNameOfPk){
		return new  Pair <String, Pair<String, String>>(calssname, new Pair<String, String>(AttrbuteName,calssNameOfPk)) ;
	}

	private static void collectInheratnceClasses (Model m){
		for(ClassData c : m.getMyClasses()){
			// if this class have parent 
			if(c.getParent()!=null){
				String parentName = c.getParent() ;
				parentName= parentName.substring(0, parentName.length()-1); // remove last index
				ClassData myRootParent = getRootOfInhertance(parentName, m);
				
				
				
				// if parent already exist before add child to its list
				if(m.getInheritanceGroups().containsKey(myRootParent)){
					m.getInheritanceGroups().get(myRootParent).add(c);
					
				}else{// create new parent nose and add chile
					ArrayList<ClassData> childs = new ArrayList<ClassData>() ; 
					childs.add(c);
					m.getInheritanceGroups().put(myRootParent, childs);
				}
			}
		}
		
		//System.out.println(m.toString());
	}
	
	 
	private static ClassData getCalssByName(String className , Model m ){
		ClassData cX = null; 
		
		for(ClassData c : m.getMyClasses()){
			//System.out.println("getCalssByName"+className+"--"+c.getName()+".");
			if(c.getName().equals(className)){
				 cX= c ;
				 //System.out.println("Founded"+className);
				break;
			}
		}
		return cX ;
	}

	
	// get the root to Inheritance it must take the initial parent to start, till if no more parent return the last one
	private static ClassData getRootOfInhertance (String calssName , Model m){
		//System.out.println(calssName);
		ClassData currentparent = getCalssByName(calssName, m) ;
		
		if(currentparent.getParent()!=null){
			currentparent = getRootOfInhertance(currentparent.getParent(), m);
		}
		
		return currentparent ;
		
	}


	// create the 3 type of inhertance calsses
	private static void createInhertanceClasses(Model m ){
		// create type 1
		createInhertanceClassesType1(m);
		// create type 3
		createInhertanceClassesType2(m);
		// create type 2
		createInhertanceClassesType3(m);
	}
	
	
	
	// create type 1 single table
	// get new Fk list for single table form concrete classes if they were have FK
	private static void createInhertanceClassesType1(Model m ){
	 
			for(ClassData parentClass : m.getInheritanceGroups().keySet()){
				 
				ClassData singleTableClass = new ClassData() ;
				// set class name
				singleTableClass.setName(parentClass.getName()+"_SINGLE_TABLE");
				// set class id PK
				singleTableClass.setPk(new Pair<String, String>("_ID", "String"));
				// add root attribute
				for(Pair<String, String> p :parentClass.getAttributes()){
					singleTableClass.getAttributes().add(p);
				}
				// add parentClass to dirty 
				m.getDirtyClasses().add(parentClass);
				// add childsAttributes and add them to dirtyClasses
				for(ClassData child : m.getInheritanceGroups().get(parentClass)){
					for(Pair<String, String> p :child.getAttributes()){
						singleTableClass.getAttributes().add(p);
					}
					m.getDirtyClasses().add(child);
					// add FK for this child to this inhertance type
					m.getFk_inhertanceType1().addAll(findFK_andUpdateNewFK(m, child.getName(), singleTableClass.getName()));
					// update all brevios FK
					updateBrevousAllocatedFK_Type1( m   , child.getName() ,singleTableClass.getName() ,   m.getFk_inhertanceType1());;
					
				}
				
				// add single table to model type1
				m.getInhertanceType1().add(singleTableClass);
				
				
			}
	}
		
		
	// create type 2 concreate Classes
	private static void createInhertanceClassesType2(Model m ){
		for(ClassData parentClass : m.getInheritanceGroups().keySet()){
			// add parent to dirty
			m.getDirtyClasses().add(parentClass);
			// create table for each child and put parent attribute to it with PK
			for(ClassData child : m.getInheritanceGroups().get(parentClass)){
				// add child to dirty
				m.getDirtyClasses().add(child);
				ClassData childTable = new ClassData() ;
				//name
				childTable.setName(child.getName()+"_CONCREATE");
				//parent attribute
				for(Pair<String , String> a : parentClass.getAttributes()){
					childTable.getAttributes().add(a);
				}
				//child attribute
				for(Pair<String , String> a : child.getAttributes()){
					childTable.getAttributes().add(a);
				}
				// PK 
				childTable.setPk(new Pair<String, String>(childTable.getName()+"_ID", "String"));
				// add childTable to model type2
				m.getInhertanceType2().add(childTable);
				m.getFk_inhertanceType2().addAll(findFK_andUpdateNewFK(m, child.getName(), childTable.getName()));
				
				updateBrevousAllocatedFK_Type2( m   , child.getName() ,childTable.getName() ,   m.getFk_inhertanceType1());;
				
			}
			 
			
			
		}
	}
		
		
	// create type 3 its own calss
	private static void createInhertanceClassesType3(Model m ){
		
		for(ClassData parentClass : m.getInheritanceGroups().keySet()){
			// add parent to dirty
			m.getDirtyClasses().add(parentClass);
			// create parent 
			ClassData ParentTabel = new ClassData() ;
			ParentTabel.setName(parentClass.getName()+"_OWNTABEL_Parent");
			// create PK
			String ParentID = ParentTabel.getName()+"_ID"; 
			ParentTabel.setPk(new Pair<String, String>(ParentID, "String"));
			// add parent attribute
			
			
			for(Pair<String , String> a : parentClass.getAttributes()){
				ParentTabel.getAttributes().add(a);
			}
			
			
			m.getInhertanceType3().add(ParentTabel);
			
			// create table for each child and put FK for Parent
			for(ClassData child : m.getInheritanceGroups().get(parentClass)){
				// add child dirty
				m.getDirtyClasses().add(child);
				ClassData childTable = new ClassData() ;
				//name
				childTable.setName(child.getName()+"_OWNTABEL_Child");
				
				//child attribute
				for(Pair<String , String> a : child.getAttributes()){
			
					childTable.getAttributes().add(a);
				}
				// PK 
				childTable.setPk(new Pair<String, String>(ParentID+"_ID", "String"));
				// FK
				m.getFk_inhertanceType3().add(new Pair<String, Pair<String,String>>(childTable.getName(), 
						new Pair<String, String>(childTable.getPk().getKey(), ParentTabel.getName())));
				// add childTable to model type2
				m.getInhertanceType3().add(childTable);
				m.getFk_inhertanceType3().addAll(findFK_andUpdateNewFK(m, child.getName(), childTable.getName()));
				
				updateBrevousAllocatedFK_Type3( m   , child.getName() ,childTable.getName() ,   m.getFk_inhertanceType1());;
				
			}
			updateBrevousAllocatedFK_Type3( m   , parentClass.getName() ,ParentTabel.getName() ,   m.getFk_inhertanceType1());;
			
			 
			
			
		}
	}
	
	
	
	// update FK table name with new name
	private static  ArrayList<Pair<String, Pair<String, String>>> findFK_andUpdateNewFK(Model m  ,  String tabelName  , String newTableName){
		
		m.getUpdatedTablesnames().put(newTableName,tabelName );
		ArrayList <Pair<String, Pair<String,String>>> FK_newlist = new ArrayList<Pair<String,Pair<String,String>>>();
		for(Pair<String, Pair<String,String>> fk  : m.getFk()){
			if(fk.getKey().equals(tabelName)){
				FK_newlist.add(new Pair<String, Pair<String,String>>(newTableName, new Pair<String, String>(fk.getValue().getKey(), fk.getValue().getValue())));
				m.getDirtyFk().add(fk);
			}else if(fk.getValue().getValue().equals(tabelName)){
				FK_newlist.add(new Pair<String, Pair<String,String>>(fk.getKey(), new Pair<String, String>(fk.getValue().getKey(), newTableName)));
				m.getDirtyFk().add(fk);
			}
		}
		
		return FK_newlist ;
	}
	
	
	    // update all FK with the new Name if the have and get there other name to update my self 
		// Because  i don't know what the previous update
		// problem how to update my self
		private static void updateBrevousAllocatedFK_Type1
						(Model m   , String tablename ,String tableNewName , ArrayList <Pair<String, Pair<String,String>>> FK_newlist){
						
						for(Pair<String, Pair<String,String>> k : m.getFk_inhertanceType1()){
							
							//  [x:y'] 
							//  convrt x->x' 
							//  get  y  of y'
						    // find y in my associacations and make them y'
							if(k.getKey().equals(tablename)){
								
								// convrt x->x' 
								k = new Pair<String, Pair<String,String>>(tableNewName, k.getValue());
								// get  y  of y'
								String oldTableName = m.getUpdatedTablesnames().get(k.getValue().getValue());
								//update myself
								for(Pair<String, Pair<String,String>> myKey : FK_newlist){
									if(myKey.getValue().getValue().equals(oldTableName)){
										myKey = new Pair<String, Pair<String,String>>(myKey.getKey(), new Pair<String, String>(myKey.getKey(), k.getValue().getValue()));
									}else if(myKey.getKey().equals(oldTableName)){
										myKey = new Pair<String, Pair<String,String>>(k.getValue().getValue(),myKey.getValue());
									}
								}
								
							}	
							//  [y':x] 
							//  convrt x->x' 
							//  get  y  of y'
						    // find y in my associacations and make them y'
							else if(k.getValue().getValue().equals(tablename)){
									
									// convrt x->x' 
									k = new Pair<String, Pair<String,String>>(k.getKey(),new Pair<String, String>(k.getKey(),tableNewName ) );
									// get  y  of y'
									String oldTableName = m.getUpdatedTablesnames().get(k.getValue().getValue());
									//update myself
									for(Pair<String, Pair<String,String>> myKey : FK_newlist){
										if(myKey.getValue().getValue().equals(oldTableName)){
											myKey = new Pair<String, Pair<String,String>>(myKey.getKey(), new Pair<String, String>(myKey.getKey(), k.getValue().getValue()));
										}else if(myKey.getKey().equals(oldTableName)){
											myKey = new Pair<String, Pair<String,String>>(k.getValue().getValue(),myKey.getValue());
										}
									}
							}
						}
		}

		
		// update all FK with the new Name if the have and get there other name to update my self 
		// Because  i don't know what the previous update
		// problem how to update my self
		private static void updateBrevousAllocatedFK_Type2
						(Model m   , String tablename ,String tableNewName , ArrayList <Pair<String, Pair<String,String>>> FK_newlist){
						
						for(Pair<String, Pair<String,String>> k : m.getFk_inhertanceType2()){
							
							//  [x:y'] 
							//  convrt x->x' 
							//  get  y  of y'
						    // find y in my associacations and make them y'
							if(k.getKey().equals(tablename)){
								
								// convrt x->x' 
								k = new Pair<String, Pair<String,String>>(tableNewName, k.getValue());
								// get  y  of y'
								String oldTableName = m.getUpdatedTablesnames().get(k.getValue().getValue());
								//update myself
								for(Pair<String, Pair<String,String>> myKey : FK_newlist){
									if(myKey.getValue().getValue().equals(oldTableName)){
										myKey = new Pair<String, Pair<String,String>>(myKey.getKey(), new Pair<String, String>(myKey.getKey(), k.getValue().getValue()));
									}else if(myKey.getKey().equals(oldTableName)){
										myKey = new Pair<String, Pair<String,String>>(k.getValue().getValue(),myKey.getValue());
									}
								}
								
							}	
							//  [y':x] 
							//  convrt x->x' 
							//  get  y  of y'
						    // find y in my associacations and make them y'
							else if(k.getValue().getValue().equals(tablename)){
									
									// convrt x->x' 
									k = new Pair<String, Pair<String,String>>(k.getKey(),new Pair<String, String>(k.getKey(),tableNewName ) );
									// get  y  of y'
									String oldTableName = m.getUpdatedTablesnames().get(k.getValue().getValue());
									//update myself
									for(Pair<String, Pair<String,String>> myKey : FK_newlist){
										if(myKey.getValue().getValue().equals(oldTableName)){
											myKey = new Pair<String, Pair<String,String>>(myKey.getKey(), new Pair<String, String>(myKey.getKey(), k.getValue().getValue()));
										}else if(myKey.getKey().equals(oldTableName)){
											myKey = new Pair<String, Pair<String,String>>(k.getValue().getValue(),myKey.getValue());
										}
									}
							}
						}
		}
		
		
		
		
		// update all FK with the new Name if the have and get there other name to update my self 
		// Because  i don't know what the previous update
		// problem how to update my self
		private static void updateBrevousAllocatedFK_Type3
						(Model m   , String tablename ,String tableNewName , ArrayList <Pair<String, Pair<String,String>>> FK_newlist){
						
						for(Pair<String, Pair<String,String>> k : m.getFk_inhertanceType3()){
							
							//  [x:y'] 
							//  convrt x->x' 
							//  get  y  of y'
						    // find y in my associacations and make them y'
							if(k.getKey().equals(tablename)){
								
								// convrt x->x' 
								k = new Pair<String, Pair<String,String>>(tableNewName, k.getValue());
								// get  y  of y'
								String oldTableName = m.getUpdatedTablesnames().get(k.getValue().getValue());
								//update myself
								for(Pair<String, Pair<String,String>> myKey : FK_newlist){
									if(myKey.getValue().getValue().equals(oldTableName)){
										myKey = new Pair<String, Pair<String,String>>(myKey.getKey(), new Pair<String, String>(myKey.getKey(), k.getValue().getValue()));
									}else if(myKey.getKey().equals(oldTableName)){
										myKey = new Pair<String, Pair<String,String>>(k.getValue().getValue(),myKey.getValue());
									}
								}
								
							}	
							//  [y':x] 
							//  convrt x->x' 
							//  get  y  of y'
						    // find y in my associacations and make them y'
							else if(k.getValue().getValue().equals(tablename)){
									
									// convrt x->x' 
									k = new Pair<String, Pair<String,String>>(k.getKey(),new Pair<String, String>(k.getKey(),tableNewName ) );
									// get  y  of y'
									String oldTableName = m.getUpdatedTablesnames().get(k.getValue().getValue());
									//update myself
									for(Pair<String, Pair<String,String>> myKey : FK_newlist){
										if(myKey.getValue().getValue().equals(oldTableName)){
											myKey = new Pair<String, Pair<String,String>>(myKey.getKey(), new Pair<String, String>(myKey.getKey(), k.getValue().getValue()));
										}else if(myKey.getKey().equals(oldTableName)){
											myKey = new Pair<String, Pair<String,String>>(k.getValue().getValue(),myKey.getValue());
										}
									}
							}
						}
		}

		
		private static void deleteDirty(Model m){
			for(ClassData c : m.getDirtyClasses()){
				//System.err.println(m.getMyClasses().size());
				m.getMyClasses().remove(c);
				//System.err.println(m.getMyClasses().size());
				c = null ;
			}
			
			for(Pair<String , Pair<String,String>> k : m.getDirtyFk()){
				//System.err.println(m.getFk().size()+" --");
				m.getFk().remove(k);
				//System.err.println(m.getFk().size()+" --");
				k = null ;
			}
		}

}
