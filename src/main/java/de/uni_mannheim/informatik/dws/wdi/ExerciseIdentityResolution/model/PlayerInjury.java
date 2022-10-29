package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
public class PlayerInjury extends AbstractRecord<Attribute> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String team;
    private String position;
    private LocalDateTime date;
    private String injuryNotes;

    public PlayerInjury(String identifier, String provenance) {
        super(identifier, provenance);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getInjuryNotes() {
        return injuryNotes;
    }

    public void setInjuryNotes(String injuryNotes) {
        this.injuryNotes = injuryNotes;
    }

    public static final Attribute NAME = new Attribute("Name");
    public static final Attribute TEAM = new Attribute("Team");
    public static final Attribute POSITION = new Attribute("Position");
    public static final Attribute DATE = new Attribute("Date");
    public static final Attribute INJURYNOTES = new Attribute("InjuryNotes");

    @Override
    public int hashCode() {
        int result = 31 + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean hasValue(Attribute attribute) {
        if(attribute==NAME)
            return name!=null;
        else if(attribute==TEAM)
            return team!=null;
        else if(attribute==POSITION)
            return position!=null;
        else if(attribute==DATE)
            return date!=null;
        else if(attribute==INJURYNOTES)
            return injuryNotes!=null;
        return false;
    }
}
