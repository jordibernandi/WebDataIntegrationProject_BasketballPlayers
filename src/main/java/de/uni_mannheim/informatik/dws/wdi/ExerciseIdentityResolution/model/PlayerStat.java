package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;


public class PlayerStat extends AbstractRecord<Attribute> implements Serializable {

	/*
	 * example entry <actor> <name>Janet Gaynor</name>
	 * <birthday>1906-01-01</birthday> <birthplace>Pennsylvania</birthplace>
	 * </actor>
	 */

	private static final long serialVersionUID = 1L;
	private String name;
    private int yearStart;
    private int yearEnd;
    private String position;
	private float height;
    private float weight;
    private String birthDate;
	private String college;

	public PlayerStat(String identifier, String provenance) {
		super(identifier, provenance);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getYearStart() {
		return yearStart;
	}
	public void setYearStart(int yearStart) {
		this.yearStart = yearStart;
	}

    public int getYearStart() {
	    return yearEnd;
	}
	public void setYearEnd(int yearEnd) {
		this.yearEnd = yearEnd;
	}
    
    public String getPosition() {
	    return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

    public String getHeight() {
	    return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}

    public String getWeight() {
	    return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

    public String getCollege() {
	    return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}

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
		PlayerStat other = (PlayerStat) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static final Attribute NAME = new Attribute("Name");
	public static final Attribute YEARSTART = new Attribute("YearStart");
	public static final Attribute YEAREND = new Attribute("YearEnd");
    public static final Attribute POSITION = new Attribute("Position");
	public static final Attribute HEIGHT = new Attribute("Height");
	public static final Attribute WEIGHT = new Attribute("Weight");
    public static final Attribute BIRTHDATE = new Attribute("Birthdate");
	public static final Attribute COLLEGE = new Attribute("College");
	
	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.Record#hasValue(java.lang.Object)
	 */
	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute==NAME)
			return name!=null;
		else if(attribute==BIRTHPLACE) 
			return birthplace!=null;
		else if(attribute==BIRTHDATE)
			return birthday!=null;
		return false;
	}
}
