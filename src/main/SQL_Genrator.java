package main;

import java.util.ArrayList;

import javafx.util.Pair;

/**
 * @problem : ORM_Mapper
 * @author  : Almgwary
 * @date    : Apr 15, 2016
 * @thin    : --M
 **/
public class SQL_Genrator {

	public static void run(ArrayList<Model> models ){
		for(Model m : models){
			
			// genrate sql code for bublic classes and bublic FK
			String publicSQLCode= "--\n";
			publicSQLCode = genrateSqlForClassesAndFKs( m ,m.getMyClasses(), m.getFk());
			
			// genrate sql code for each type of inhertance with its own FK
			String SQLCode_inhertanceType1= "--\n";
			SQLCode_inhertanceType1 = genrateSqlForClassesAndFKs(m ,m.getInhertanceType1(), m.getFk_inhertanceType1());
			
			
			String SQLCode_inhertanceType2= "--\n";
			SQLCode_inhertanceType2 = genrateSqlForClassesAndFKs(m ,m.getInhertanceType2(), m.getFk_inhertanceType2());
			
			
			
			String SQLCode_inhertanceType3= "--\n";
			SQLCode_inhertanceType3 = genrateSqlForClassesAndFKs(m ,m.getInhertanceType3(), m.getFk_inhertanceType3());
			
			
			// concatinate sql genration for each type
			SQLCode_inhertanceType1 = publicSQLCode + "\n" + SQLCode_inhertanceType1 + "\n\n" ; 
			SQLCode_inhertanceType2 = publicSQLCode + "\n" + SQLCode_inhertanceType2 + "\n\n"  ; 
			SQLCode_inhertanceType3 = publicSQLCode + "\n" + SQLCode_inhertanceType3 + "\n\n" ; 
			
			SQLCode_inhertanceType1= "\n----------------------------------------"
					  +  "\n-- SINGLE TABLE						  "
					  +  "\n----------------------------------------"
					  + "\n"+SQLCode_inhertanceType1;


			m.setSQLCode_inhertanceType1(SQLCode_inhertanceType1);



			
			SQLCode_inhertanceType2= "\n----------------------------------------"
		  			  +  "\n-- ConcReate Classes						  "
		              +  "\n----------------------------------------"
		              +  "\n"+SQLCode_inhertanceType2;


			m.setSQLCode_inhertanceType2(SQLCode_inhertanceType2);

			
			SQLCode_inhertanceType3= "\n----------------------------------------"
								  +  "\n-- Its OWn Table							  "
								  +  "\n----------------------------------------"
								  +  "\n"+SQLCode_inhertanceType3;


			m.setSQLCode_inhertanceType3(SQLCode_inhertanceType3);

			
			 
			
			
		}
		
		
		
	}
	
	
	
	
	
	// genrate for groub of classes and groub of FK
			private static String  genrateSqlForClassesAndFKs (Model m ,ArrayList<ClassData> classes, ArrayList<Pair<String, Pair <String, String>>> Fks){
				String sql = "--"+"\n" ;
				for(ClassData c : classes){
					sql += genrateSqlForClass(c);
				}
				
				for (Pair<String, Pair <String, String>> k : Fks){
					sql+= genrateSqlForFK(k , m);
				}
			
				
				return sql+"\n" ;
			}
			
			
			
			
			
			// make class name 
			// key if exist
			// attributes
			
			private static String genrateSqlForClass(ClassData c){
				String sql = "--"+"\n";
				if(c!=null){
					sql = "CREATE TABLE `db2`.`"+c.getName()+"` (";
					// add attribute
					for(Pair<String, String> a : c.getAttributes()){
						sql += "  `"+a.getKey()+"` "+(a.getValue().equals("String")? "VARCHAR(45)" :a.getValue() )+" NULL,";
					}		
					// add PK  if exist
					
					if(c.getPk()!=null)
					{
						//System.err.println(c.getName()+ c.getPk().getKey()+"--");	
						sql += "  `"+c.getPk().getKey()+"`  "+(c.getPk().getValue().equals("String")? "VARCHAR(45)" :c.getPk().getValue() )+" NOT NULL,"+ "  PRIMARY KEY (`"+c.getPk().getKey()+"`)";
						
					}
						
					//close sql statment
					sql += ");" ;

 


				}
				
				return sql+"\n" ;
			}
			
			
			// make fk for class 1 
			// connect to class  2  PK key 
			private static String genrateSqlForFK(Pair<String, Pair <String, String>> fk ,Model m){
				String sql = "--";
				String tableName =  fk.getKey() ; 
				String fkName =  fk.getValue().getKey() ;
				String refrenceTable =  fk.getValue().getValue();
				String refrenc_pK  = "" ; 
				ClassData c = getClassByName(m , refrenceTable) ;
				if(c!=null ){
					
					refrenc_pK = c.getPk().getKey();
				 sql = "ALTER TABLE `db2`.`"+tableName+"`"
							+ "ADD INDEX `"+fkName+"_idx` (`"+fkName+"` ASC);"
							+ "ALTER TABLE `db2`.`"+tableName+"` "
							+ "ADD CONSTRAINT `"+fkName+"`"
							+ "  FOREIGN KEY (`"+fkName+"`)"
							+ "  REFERENCES `db2`.`"+refrenceTable+"` (`"+refrenc_pK+"`)"
							+ "  ON DELETE NO ACTION"
							+ "  ON UPDATE NO ACTION;"  ;
				}
				
				return sql+"\n" ;
			}
			
			
			private static ClassData getClassByName (Model m , String tableName ){
				ClassData c = null ;
				
				for(ClassData x : m.getMyClasses()){
					if(x.getName().equals(tableName))
					{
						return x;
						 
					}	
				}
				
				
				for(ClassData x : m.getInhertanceType1()){
					if(x.getName().equals(tableName))
					{
						return x;
					}	
				}
				
				
				for(ClassData x : m.getInhertanceType2()){
					if(x.getName().equals(tableName))
					{
						return x;
					}	
				}
				
				
				for(ClassData x : m.getInhertanceType3()){
					if(x.getName().equals(tableName))
					{
						return x;
					}	
				}
				
				
				 
				
				return c ; 
			}
	
}
