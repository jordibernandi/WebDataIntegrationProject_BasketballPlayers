package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
public class PlayerDBpedia extends AbstractRecord<Attribute> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private LocalDateTime birthDate;
    private String birthPlace;
    private double height;
    private double weight;
    private int startYear;
    private int endYear;
    private List<String> leagues;
    private List<String> positions;
    private List<String> teams;
    private List<String> awards;

    public PlayerDBpedia(String identifier, String provenance) {
        super(identifier, provenance);
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
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

    public static final Attribute NAME = new Attribute("Name");
    public static final Attribute BIRTHDATE = new Attribute("BirthDate");
    public static final Attribute BIRTHPLACE = new Attribute("BirthPlace");
    public static final Attribute HEIGHT = new Attribute("Height");
    public static final Attribute WEIGHT = new Attribute("Weight");
    public static final Attribute STARTYEAR = new Attribute("StartYear");
    public static final Attribute ENDYEAR = new Attribute("EndYear");
    public static final Attribute LEAGUES = new Attribute("Leagues");
    public static final Attribute POSITIONS = new Attribute("Positions");
    public static final Attribute TEAMS = new Attribute("Teams");
    public static final Attribute AWARDS = new Attribute("Awards");


    @Override
    public int hashCode() {
        int result = 31 + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean hasValue(Attribute attribute) {
        if(attribute==NAME)
            return name!=null;
        else if(attribute==BIRTHDATE)
            return birthDate!=null;
        else if(attribute==BIRTHPLACE)
            return birthPlace!=null;
        else if(attribute==HEIGHT)
            return height!=0;
        else if(attribute==WEIGHT)
            return weight!=0;
        else if(attribute==STARTYEAR)
            return startYear!=0;
        else if(attribute==ENDYEAR)
            return endYear!=0;
        else if(attribute==LEAGUES)
            return leagues!=null && leagues.size() > 0;
        else if(attribute==POSITIONS)
            return positions!=null && positions.size() > 0;
        else if(attribute==TEAMS)
            return teams!=null && teams.size() > 0;
        else if(attribute==AWARDS)
            return awards!=null && awards.size() > 0;
        return false;
    }
}
