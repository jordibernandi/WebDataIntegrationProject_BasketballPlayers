package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import org.apache.commons.lang3.StringUtils;

public class PlayerXMLReader extends XMLMatchableReader<Player, Attribute> implements
				FusibleFactory<Player, Attribute> {

    @Override
    protected void initialiseDataset(DataSet<Player, Attribute> dataset) {
        super.initialiseDataset(dataset);

        dataset.addAttribute(Player.NAME);
        dataset.addAttribute(Player.BIRTHDATE);
        dataset.addAttribute(Player.BIRTHPLACE);
        dataset.addAttribute(Player.YEARSTART);
        dataset.addAttribute(Player.YEAREND);
        dataset.addAttribute(Player.HEIGHT);
        dataset.addAttribute(Player.WEIGHT);
        dataset.addAttribute(Player.COLLEGE);
        dataset.addAttribute(Player.SALARIES);
        dataset.addAttribute(Player.POSITIONS);
        dataset.addAttribute(Player.LEAGUES);
        dataset.addAttribute(Player.TEAMS);
        dataset.addAttribute(Player.AWARDS);
        dataset.addAttribute(Player.INJURIES);
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
                LocalDate dt = LocalDate.parse(date, formatter);
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

        player.setLeagues(getListFromChildElement(node, "league"));
        player.setPositions(getListFromChildElement(node, "position"));
        player.setTeams(getListFromChildElement(node, "team"));
        player.setAwards(getListFromChildElement(node, "award"));

//        List<String> positions = getListFromChildElement(node, "position");
//        if (positions != null) {
//            player.setPositions(positions);
//        } else {
//            player.setPositions(Arrays.asList(""));
//        }
//
//        List<String> teams = getListFromChildElement(node, "team");
//        if (teams != null) {
//            player.setTeams(teams);
//        } else {
//            player.setTeams(Arrays.asList(""));
//        }

//        List<String> leagues = getListFromChildElement(node, "league");
//        if (leagues != null) {
//            player.setLeagues(leagues);
//        } else {
//            player.setLeagues(Arrays.asList(""));
//        }

//        List<String> awards = getListFromChildElement(node, "award");
//        if (awards != null) {
//            player.setAwards(awards);
//        } else {
//            player.setAwards(Arrays.asList(""));
//        }

        // load the list of salaries
        List<Salary> salaries = getObjectListFromChildElement(node, "salaries",
                "salary", new SalaryXMLReader(), provenanceInfo);
        player.setSalaries(salaries);

        // load the list of injuries
        List<Injury> injuries = getObjectListFromChildElement(node, "injuries",
                "injury", new InjuryXMLReader(), provenanceInfo);
        player.setInjuries(injuries);
        return player;
    }

	@Override
	public Player createInstanceForFusion(RecordGroup<Player, Attribute> cluster) {
		// TODO Auto-generated method stub
		List<String> ids = new LinkedList<>();

        for (Player p : cluster.getRecords()) {
            ids.add(p.getIdentifier());
        }

        Collections.sort(ids);

        String mergedId = StringUtils.join(ids, '+');

        return new Player(mergedId, "fused");
	}
}
