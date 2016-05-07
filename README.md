# MYSQL_ORM_Genrator
genrate MYSQL form any class model like next input format .  
	class may have inhertance and associations, 
	ORM map to   
				1.Single Table  
				2.Concreate Table  
				3.Each class Table  

## input example 


		class Person
		attributes
			name : String
			SSN : String
		end
		-- Inheritance relation
		class Employee < Person
		attributes
		 salary : Integer
		end
		class Department 
		attributes
		 name : String
		 location : String
		 budget : Integer
		end
		class Project
		attributes
		 name : String
		 budget : Integer
		end
		-- associations

		association worksIn between
		 Employee[*]
		 Department[1--*]
		end
		association workson between
		 Employee[*]
		 Project[*]
		end

		association Controls between
		 Department[1]
		 Project[*]
		end


