package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.List;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.StringUtils;


public class Player extends AbstractRecord<Attribute> implements Serializable {

    protected String id;
    protected String provenance;
    private String name;
    private LocalDateTime birthDate;
    private String birthPlace;
    private int yearStart;
    private int yearEnd;
    private float height;
    private float weight;
    private String college;
    private List<Salary> salaries;
    private List<String> positions;
    private List<String> leagues;
    private List<String> teams;
    private List<String> awards;
    private List<Injury> injuries;
    
    public static final Attribute NAME = new Attribute("Name");
    public static final Attribute BIRTHDATE = new Attribute("BirthDate");
    public static final Attribute BIRTHPLACE = new Attribute("BirthPlace");
    public static final Attribute POSITIONS = new Attribute("Positions");
    public static final Attribute YEARSTART = new Attribute("YearStart");
    public static final Attribute YEAREND = new Attribute("YearEnd");
    public static final Attribute HEIGHT = new Attribute("Height");
    public static final Attribute WEIGHT = new Attribute("Weight");
    public static final Attribute COLLEGE = new Attribute("College");
    public static final Attribute SALARIES = new Attribute("Salaries");
    public static final Attribute LEAGUES = new Attribute("Leagues");
    public static final Attribute TEAMS = new Attribute("Teams");
    public static final Attribute AWARDS = new Attribute("Awards");
    public static final Attribute INJURIES = new Attribute("Injuries");

    public Player(String identifier, String provenance) {
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

    public LocalDateTime getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
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

    public String getCollege() {
        return college;
    }
    public void setCollege(String college) {
        this.college = college;
    }

    public List<Salary> getSalaries() {
        return salaries;
    }
    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }

    public List<String> getLeagues() {
        return leagues;
    }
    public void setLeagues(List<String> leagues) {
        this.leagues = leagues;
    }

    public List<String> getPositions() {
        return positions;
    }
    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    public List<String> getTeams() {
        return teams;
    }
    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    public List<String> getAwards() {
        return awards;
    }
    public void setAwards(List<String> awards) {
        this.awards = awards;
    }

    public List<Injury> getInjuries() {
        return injuries;
    }
    public void setInjuries(List<Injury> injuries) {
        this.injuries = injuries;
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
        if(obj instanceof Player){
            return this.getIdentifier().equals(((Player) obj).getIdentifier());
        }else
            return false;
    }
    
    private Map<Attribute, Collection<String>> provenanceMap = new HashMap<>();
    private Collection<String> recordProvenance;

    public void setRecordProvenance(Collection<String> provenance) {
        recordProvenance = provenance;
    }

    public Collection<String> getRecordProvenance() {
        return recordProvenance;
    }

    public void setAttributeProvenance(Attribute attribute, Collection<String> provenance) {
        this.provenanceMap.put(attribute, provenance);
    }

    public Collection<String> getAttributeProvenance(String attribute) {
        return provenanceMap.get(attribute);
    }

    public String getMergedAttributeProvenance(Attribute attribute) {
        Collection<String> prov = provenanceMap.get(attribute);

        if (prov != null) {
            return StringUtils.join(prov, "+");
        } else {
            return "";
        }
    }

	@Override
	public boolean hasValue(Attribute attribute) {
		// TODO Auto-generated method stub
		return false;
	}
}
