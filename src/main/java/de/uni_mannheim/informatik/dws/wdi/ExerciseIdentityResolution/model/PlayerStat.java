package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Locale;
import java.util.List;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;


public class PlayerStat implements Matchable {

	protected String id;
	protected String provenance;
	private String name;
    private int yearStart;
    private int yearEnd;
    private List<String> positions;
	private float height;
    private float weight;
    private LocalDateTime birthDate;
	private String college;

	public PlayerStat(String identifier, String provenance) {
		id = identifier;
		this.provenance = provenance;
	}

	@Override
	public String getIdentifier() {
		return id;
	}

	@Override
	public String getProvenance() {
		return provenance;
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

    public int getYearEnd() {
	    return yearEnd;
	}
	public void setYearEnd(int yearEnd) {
		this.yearEnd = yearEnd;
	}

	public List<String> getPositionss() {
		return positions;
	}

	public void setPositions(List<String> positions) {
		this.positions = positions;
	}

    public float getHeight() {
	    return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}

    public float getWeight() {
	    return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}

	public LocalDateTime getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDateTime birthDate) {
		this.birthDate = birthDate;
	}

    public String getCollege() {
	    return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}

	@Override
	public String toString() {
		return String.format("[PlayerStat %s: %s / %s]", getIdentifier(), getName(),
				getBirthDate());
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PlayerStat){
			return this.getIdentifier().equals(((PlayerStat) obj).getIdentifier());
		}else
			return false;
	}
}
