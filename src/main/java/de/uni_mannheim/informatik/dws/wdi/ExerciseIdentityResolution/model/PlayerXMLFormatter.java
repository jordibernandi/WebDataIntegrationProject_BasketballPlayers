package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PlayerXMLFormatter extends XMLFormatter<Player> {

    @Override
    public Element createRootElement(Document doc) {
        return doc.createElement("players");
    }

    @Override
    public Element createElementFromRecord(Player record, Document doc) {
        Element player = doc.createElement("player");

        player.appendChild(createTextElement("id", record.getIdentifier(), doc));

        player.appendChild(createTextElementWithProvenance("name", record.getName(),
                record.getMergedAttributeProvenance(Player.NAME), doc));

        player.appendChild(createTextElementWithProvenance("birthplace", record.getBirthPlace(),
                record.getMergedAttributeProvenance(Player.BIRTHPLACE), doc));

        if (record.getBirthDate() != null) {
            player.appendChild(createTextElementWithProvenance("birthdate", record.getBirthDate().toString(),
                    record.getMergedAttributeProvenance(Player.BIRTHDATE), doc));
        }
        
        if (!Float.toString(record.getHeight()).isEmpty()) {
            player.appendChild(createTextElementWithProvenance("height", Float.toString(record.getHeight()),
                    record.getMergedAttributeProvenance(Player.HEIGHT), doc));
        }

        if (!Float.toString(record.getWeight()).isEmpty()) {
            player.appendChild(createTextElementWithProvenance("weight", Float.toString(record.getWeight()),
                    record.getMergedAttributeProvenance(Player.WEIGHT), doc));
        }
        
        if (record.getCollege() != null) {
            player.appendChild(createTextElementWithProvenance("college", record.getCollege().toString(),
                    record.getMergedAttributeProvenance(Player.COLLEGE), doc));
        }
      
        if (!Integer.toString(record.getYearStart()).isEmpty()) {
            player.appendChild(createTextElementWithProvenance("yearstart", Integer.toString(record.getYearStart()),
                    record.getMergedAttributeProvenance(Player.YEARSTART), doc));
        }
        
        if (!Integer.toString(record.getYearEnd()).isEmpty()) {
            player.appendChild(createTextElementWithProvenance("yearend", Integer.toString(record.getYearEnd()),
                    record.getMergedAttributeProvenance(Player.YEAREND), doc));
        }

        List<String> positions = record.getPositions();
        if (positions != null && !positions.isEmpty()) {
            Element positions1 = doc.createElement("positions");
            for (int i = 0; i < record.getPositions().size(); i++) {
            	positions1
                        .appendChild(createTextElementWithProvenance("position", record.getPositions().get(i),
                                record.getMergedAttributeProvenance(Player.POSITIONS), doc));
            }
            player.appendChild(positions1);
        }

//        List<Salary> salaries = record.getSalaries();
//        if (salaries != null && !salaries.isEmpty()) {
//            Element salaries1 = doc.createElement("salaries");
//            for (int i = 0; i < record.getSalaries().size(); i++) {
//            	salaries1
//                        .appendChild(createTextElementWithProvenance("salary", record.getSalaries().get(i),
//                                record.getMergedAttributeProvenance(Player.SALARIES), doc));
//            }
//            player.appendChild(salaries1);
//        }
//        List<Injury> injuries = record.getInjuries();
//        if (injuries != null && !injuries.isEmpty()) {
//            Element injuries1 = doc.createElement("injuries");
//            for (int i = 0; i < record.getInjuries().size(); i++) {
//            	injuries1
//                        .appendChild(createTextElementWithProvenance("injury", record.getInjuries().get(i),
//                                record.getMergedAttributeProvenance(Player.INJURIES), doc));
//            }
//            player.appendChild(injuries1);
//        }
        List<String> teams = record.getTeams();
        if (teams != null && !teams.isEmpty()) {
            Element teams1 = doc.createElement("teams");
            for (int i = 0; i < record.getTeams().size(); i++) {
                teams1
                        .appendChild(createTextElementWithProvenance("team", record.getTeams().get(i),
                                record.getMergedAttributeProvenance(Player.TEAMS), doc));
            }
            player.appendChild(teams1);
        }
        List<String> leagues = record.getLeagues();
        if (leagues != null && !leagues.isEmpty()) {
            Element leagues1 = doc.createElement("leagues");
            for (int i = 0; i < record.getLeagues().size(); i++) {
            	leagues1
                        .appendChild(createTextElementWithProvenance("league", record.getLeagues().get(i),
                                record.getMergedAttributeProvenance(Player.LEAGUES), doc));
            }
            player.appendChild(leagues1);
        }
        List<String> awards = record.getAwards();
        if (awards != null && !awards.isEmpty()) {
            Element awards1 = doc.createElement("awards");
            for (int i = 0; i < record.getAwards().size(); i++) {
            	awards1
                        .appendChild(createTextElementWithProvenance("award", record.getAwards().get(i),
                                record.getMergedAttributeProvenance(Player.AWARDS), doc));
            }
            player.appendChild(awards1);
        }

        return player;
    }

    protected Element createTextElementWithProvenance(String name, String value, String provenance, Document doc) {
        Element elem = createTextElement(name, value, doc);
        elem.setAttribute("provenance", provenance);
        return elem;

    }

}