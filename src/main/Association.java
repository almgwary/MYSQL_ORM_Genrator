package main;

import javafx.util.Pair;

/**
 * @problem : ORM_Mapper
 * @author  : Almgwary
 * @date    : Apr 7, 2016
 * @thin    : --M
 **/
public class Association {
	String name ;
	/*for firstCalssName and minimum and maximum values*/
	Pair<String, Pair<String, String>> firstRules  ;
	/*for secondCalssName and minimum and maximum values*/
	Pair<String, Pair<String, String>> secondtRules  ;
	 
	public Association() {}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\n   "
				+ "\n+------------------------+"
				+ "\n+ Association:" + name + "         "
				+ "\n+------------------------+"
				+ "\n|" + firstRules + ""
				+ "\n|" + secondtRules + ""
				+ "\n+------------------------+\n";
	}


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
	 * @return the firstRules
	 */
	public Pair<String, Pair<String, String>> getFirstRules() {
		return firstRules;
	}

	/**
	 * @param firstRules the firstRules to set
	 */
	public void setFirstRules(Pair<String, Pair<String, String>> firstRules) {
		this.firstRules = firstRules;
	}

	/**
	 * @return the secondtRules
	 */
	public Pair<String, Pair<String, String>> getSecondtRules() {
		return secondtRules;
	}

	/**
	 * @param secondtRules the secondtRules to set
	 */
	public void setSecondtRules(Pair<String, Pair<String, String>> secondtRules) {
		this.secondtRules = secondtRules;
	}
		
}
