/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class PlayerSalary extends AbstractRecord<Attribute> implements Serializable {

	private static final long serialVersionUID = 1L;
	private int registerValue;
	private String name;
	private double salary;
	private int startYear;
	private int endYear;
	private String team;
	private String fullTeamName;
	
	public PlayerSalary(String identifier, String provenance) {
		super(identifier, provenance);
	}

	public int getRegisterValue() {
		return registerValue;
	}

	public void setRegisterValue(int registerValue) {
		this.registerValue = registerValue;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	
	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	
	public String getFullTeamName() {
		return fullTeamName;
	}

	public void setFullTeamName(String fullTeamName) {
		this.fullTeamName = fullTeamName;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 31 + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerSalary other = (PlayerSalary) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static final Attribute REGISTERVALUE = new Attribute("RegisterValue");
	public static final Attribute NAME = new Attribute("Name");
	public static final Attribute SALARY = new Attribute("Salary");
	public static final Attribute STARTYEAR = new Attribute("StartYear");
	public static final Attribute ENDYEAR = new Attribute("EndYear");
	public static final Attribute TEAM = new Attribute("Team");
	public static final Attribute FULLTEAMNAME = new Attribute("FullTeamName");
	
	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.Record#hasValue(java.lang.Object)
	 */
	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute==REGISTERVALUE)
			return name!=null;
		else if(attribute==NAME) 
			return registerValue!=0;
		else if(attribute==SALARY)
			return salary!=0;
		else if(attribute==STARTYEAR)
			return startYear!=0;
		else if(attribute==ENDYEAR)
			return endYear!=0;
		else if(attribute==TEAM)
			return team!=null;
		else if(attribute==FULLTEAMNAME)
			return fullTeamName!=null;
		return false;
	}
}

