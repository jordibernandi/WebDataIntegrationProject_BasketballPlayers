package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

/**
 * A {@link XMLMatchableReader} for {@link Actor}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class PlayerStatXMLReader extends XMLMatchableReader<Actor, Attribute> {

	@Override
	public Playerstat createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		Playerstat playerstat = new Playerstat(id, provenanceInfo);

		// fill the attributes
		Playerstat.setName(getValueFromChildElement(node, "name"));
		Playerstat.setYearStart(getValueFromChildElement(node, "yearstar"));
        Playerstat.setYearEnd(getValueFromChildElement(node, "yearend"));
		Playerstat.setPosition(getValueFromChildElement(node, "position"));
        Playerstat.setHeight(getValueFromChildElement(node, "height"));
		Playerstat.setWeight(getValueFromChildElement(node, "weight"));
        Playerstat.setCollege(getValueFromChildElement(node, "college"));

		// convert the date string into a DateTime object
		try {
			String date = getValueFromChildElement(node, "birthdate");
			if (date != null) {
				DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				        .appendPattern("yyyy-MM-dd")
				        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
				        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
				        .toFormatter(Locale.ENGLISH);
				LocalDateTime dt = LocalDateTime.parse(date, formatter);
				actor.setBirthday(dt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Playerstat;
	}

