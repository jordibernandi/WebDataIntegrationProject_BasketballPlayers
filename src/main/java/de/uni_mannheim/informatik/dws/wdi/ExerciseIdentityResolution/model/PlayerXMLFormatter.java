package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PlayerXMLFormatter extends XMLFormatter<Player> {

    SalaryXMLFormatter salaryFormatter = new SalaryXMLFormatter();
    InjuryXMLFormatter injuryFormatter = new InjuryXMLFormatter();

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

        if (record.getBirthDate() != null) {
            player.appendChild(createTextElementWithProvenance("birthDate", record.getBirthDate().toString(),
                    record.getMergedAttributeProvenance(Player.BIRTHDATE), doc));
        }

        if (record.getBirthPlace() != null) {
            player.appendChild(createTextElementWithProvenance("birthPlace", record.getBirthPlace(),
                    record.getMergedAttributeProvenance(Player.BIRTHPLACE), doc));
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
            player.appendChild(createTextElementWithProvenance("yearStart", Integer.toString(record.getYearStart()),
                    record.getMergedAttributeProvenance(Player.YEARSTART), doc));
        }
        
        if (!Integer.toString(record.getYearEnd()).isEmpty()) {
            player.appendChild(createTextElementWithProvenance("yearEnd", Integer.toString(record.getYearEnd()),
                    record.getMergedAttributeProvenance(Player.YEAREND), doc));
        }

        List<String> leagues = record.getLeagues();
        if (leagues != null && !leagues.isEmpty()) {
            Element leagues1 = doc.createElement("league");
            for (int i = 0; i < record.getLeagues().size(); i++) {
                leagues1
                        .appendChild(createTextElementWithProvenance("name", record.getLeagues().get(i),
                                record.getMergedAttributeProvenance(Player.LEAGUES), doc));
            }
            player.appendChild(leagues1);
        }

        List<String> teams = record.getTeams();
        if (teams != null && !teams.isEmpty()) {
            Element teams1 = doc.createElement("team");
            for (int i = 0; i < record.getTeams().size(); i++) {
                teams1
                        .appendChild(createTextElementWithProvenance("name", record.getTeams().get(i),
                                record.getMergedAttributeProvenance(Player.TEAMS), doc));
            }
            player.appendChild(teams1);
        }

        List<String> positions = record.getPositions();
        if (positions != null && !positions.isEmpty()) {
            Element positions1 = doc.createElement("position");
            for (int i = 0; i < record.getPositions().size(); i++) {
            	positions1
                        .appendChild(createTextElementWithProvenance("name", record.getPositions().get(i),
                                record.getMergedAttributeProvenance(Player.POSITIONS), doc));
            }
            player.appendChild(positions1);
        }

        List<String> awards = record.getAwards();
        if (awards != null && !awards.isEmpty()) {
            Element awards1 = doc.createElement("award");
            for (int i = 0; i < record.getAwards().size(); i++) {
            	awards1
                        .appendChild(createTextElementWithProvenance("name", record.getAwards().get(i),
                                record.getMergedAttributeProvenance(Player.AWARDS), doc));
            }
            player.appendChild(awards1);
        }

        player.appendChild(createSalariesElement(record, doc));
        player.appendChild(createInjuriesElement(record, doc));

        return player;
    }

    protected Element createTextElementWithProvenance(String name, String value, String provenance, Document doc) {
        Element elem = createTextElement(name, value, doc);
        elem.setAttribute("provenance", provenance);
        return elem;

    }

    protected Element createSalariesElement(Player record, Document doc) {
        Element salaryRoot = salaryFormatter.createRootElement(doc);
        salaryRoot.setAttribute("provenance",
                record.getMergedAttributeProvenance(Player.SALARIES));

        for (Salary s : record.getSalaries()) {
            salaryRoot.appendChild(salaryFormatter
                    .createElementFromRecord(s, doc));
        }

        return salaryRoot;
    }

    protected Element createInjuriesElement(Player record, Document doc) {
        Element injuryRoot = injuryFormatter.createRootElement(doc);
        injuryRoot.setAttribute("provenance",
                record.getMergedAttributeProvenance(Player.INJURIES));

        for (Injury i : record.getInjuries()) {
            injuryRoot.appendChild(injuryFormatter
                    .createElementFromRecord(i, doc));
        }

        return injuryRoot;
    }
}