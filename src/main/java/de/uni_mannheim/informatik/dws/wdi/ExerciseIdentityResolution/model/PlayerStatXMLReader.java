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

public class PlayerStatXMLReader extends XMLMatchableReader<PlayerStat, Attribute> {

	@Override
	protected void initialiseDataset(DataSet<PlayerStat, Attribute> dataset) {
		super.initialiseDataset(dataset);

	}

	@Override
	public PlayerStat createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		PlayerStat playerStat = new PlayerStat(id, provenanceInfo);

		// fill the attributes
		playerStat.setName(getValueFromChildElement(node, "name"));

		String yearStart = getValueFromChildElement(node, "yearStart");
		if (yearStart != null && !yearStart.isEmpty()) {
			playerStat.setYearStart(Integer.parseInt(yearStart));
		}

		String yearEnd = getValueFromChildElement(node, "yearEnd");
		if (yearEnd != null && !yearEnd.isEmpty()) {
			playerStat.setYearStart(Integer.parseInt(yearEnd));
		}

		playerStat.setPositions(getListFromChildElement(node, "position"));

		String height = getValueFromChildElement(node, "height");
		if (height != null && !height.isEmpty()) {
			playerStat.setHeight(Float.parseFloat(height));
		}

		String weight = getValueFromChildElement(node, "weight");
		if (weight != null && !weight.isEmpty()) {
			playerStat.setWeight(Float.parseFloat(weight));
		}

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
				playerStat.setBirthDate(dt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		playerStat.setCollege(getValueFromChildElement(node, "college"));

		return playerStat;
	}
}
