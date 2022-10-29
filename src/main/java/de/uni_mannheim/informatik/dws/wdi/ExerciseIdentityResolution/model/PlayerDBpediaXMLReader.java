package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import org.w3c.dom.Node;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class PlayerDBpediaXMLReader extends XMLMatchableReader<PlayerDBpedia, Attribute> {

    @Override
    protected void initialiseDataset(DataSet<PlayerDBpedia, Attribute> dataset) {
        super.initialiseDataset(dataset);

    }
    @Override
    public PlayerDBpedia createModelFromElement(Node node, String provenanceInfo) {
        String id = UUID.randomUUID().toString();

        // create the object with id and provenance information
        PlayerDBpedia playerDBpedia = new PlayerDBpedia(id, provenanceInfo);

        // fill the attributes
        playerDBpedia.setName(getValueFromChildElement(node, "name"));

        // convert the date string into a DateTime object
        try {
            String date = getValueFromChildElement(node, "birthDate");
            if (date != null) {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd")
                        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .toFormatter(Locale.ENGLISH);
                LocalDateTime dt = LocalDateTime.parse(date, formatter);
                playerDBpedia.setBirthDate(dt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        playerDBpedia.setBirthPlace(getValueFromChildElement(node, "birthPlace"));
        playerDBpedia.setHeight(Double.parseDouble(getValueFromChildElement(node, "height")));
        playerDBpedia.setWeight(Double.parseDouble(getValueFromChildElement(node, "weight")));
        playerDBpedia.setStartYear(Integer.valueOf(getValueFromChildElement(node, "startYear")));
        playerDBpedia.setEndYear(Integer.valueOf(getValueFromChildElement(node, "endYear")));

        playerDBpedia.setLeagues(new ArrayList<String>(getListFromChildElement(node, "leagues")));
        playerDBpedia.setPositions(new ArrayList<String>(getListFromChildElement(node, "positions")));
        playerDBpedia.setTeams(new ArrayList<String>(getListFromChildElement(node, "teams")));
        playerDBpedia.setAwards(new ArrayList<String>(getListFromChildElement(node, "awards")));

        return playerDBpedia;
    }
}
