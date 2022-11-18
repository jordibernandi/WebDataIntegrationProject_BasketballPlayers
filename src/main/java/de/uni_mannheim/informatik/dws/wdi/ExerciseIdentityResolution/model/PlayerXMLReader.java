package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class PlayerXMLReader extends XMLMatchableReader<Player, Attribute> {

    @Override
    protected void initialiseDataset(DataSet<Player, Attribute> dataset) {
        super.initialiseDataset(dataset);

    }

    @Override
    public Player createModelFromElement(Node node, String provenanceInfo) {
        String id = getValueFromChildElement(node, "id");

        // create the object with id and provenance information
        Player player = new Player(id, provenanceInfo);

        // fill the attributes
        player.setName(getValueFromChildElement(node, "name"));

        // convert the date string into a DateTime object
        try {
            String date = getValueFromChildElement(node, "birthDate");
            if (date != null && !date.isEmpty()) {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd")
                        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .toFormatter(Locale.ENGLISH);
                LocalDateTime dt = LocalDateTime.parse(date, formatter);
                player.setBirthDate(dt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String birthPlace = getValueFromChildElement(node, "birthPlace");
        if (birthPlace != null && !birthPlace.isEmpty()) {
            player.setBirthPlace(birthPlace);
        }

        String yearStart = getValueFromChildElement(node, "yearStart");
        if (yearStart != null && !yearStart.isEmpty()) {
            player.setYearStart(Integer.parseInt(yearStart));
        }

        String yearEnd = getValueFromChildElement(node, "yearEnd");
        if (yearEnd != null && !yearEnd.isEmpty()) {
            player.setYearEnd(Integer.parseInt(yearEnd));
        }

        String height = getValueFromChildElement(node, "height");
        if (height != null && !height.isEmpty()) {
            player.setHeight(Float.parseFloat(height));
        }

        String weight = getValueFromChildElement(node, "weight");
        if (weight != null && !weight.isEmpty()) {
            player.setWeight(Float.parseFloat(weight));
        }

        String college = getValueFromChildElement(node, "college");
        if (college != null && !college.isEmpty()) {
            player.setCollege(college);
        }

        // load the list of salaries
        List<Salary> salaries = getObjectListFromChildElement(node, "salaries",
                "salary", new SalaryXMLReader(), provenanceInfo);
        player.setSalaries(salaries);

        player.setPositions(getListFromChildElement(node, "position"));
        player.setTeams(getListFromChildElement(node, "team"));
        player.setAwards(getListFromChildElement(node, "award"));

        // load the list of injuries
        List<Injury> injuries = getObjectListFromChildElement(node, "injuries",
                "injury", new InjuryXMLReader(), provenanceInfo);
        player.setInjuries(injuries);

        return player;
    }
}
