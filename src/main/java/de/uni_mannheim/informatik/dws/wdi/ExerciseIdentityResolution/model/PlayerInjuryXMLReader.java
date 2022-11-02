package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class PlayerInjuryXMLReader extends XMLMatchableReader<PlayerInjury, Attribute>  {

	@Override
	protected void initialiseDataset(DataSet<PlayerInjury, Attribute> dataset) {
		super.initialiseDataset(dataset);
		
	}
	
	@Override
	public PlayerInjury createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");


		// create the object with id and provenance information
		PlayerInjury playerInjury = new PlayerInjury(id, provenanceInfo);

		// fill the attributes
		playerInjury.setName(getValueFromChildElement(node, "playerName"));
		playerInjury.setTeam(getValueFromChildElement(node, "teamName"));
		playerInjury.setPosition(getValueFromChildElement(node, "position"));

		// convert the date string into a DateTime object
		try {
			String date = getValueFromChildElement(node, "date");
			if (date != null) {
				DateTimeFormatter formatter = new DateTimeFormatterBuilder()
						.appendPattern("yyyy-MM-dd")
						.parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
						.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
						.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
						.toFormatter(Locale.ENGLISH);
				LocalDateTime dt = LocalDateTime.parse(date, formatter);
				playerInjury.setDate(dt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		playerInjury.setInjuryNotes(getValueFromChildElement(node, "injuryNotes"));

		return playerInjury;
	}
}
