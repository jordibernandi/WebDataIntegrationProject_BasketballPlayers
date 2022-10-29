package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import java.util.UUID;

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
		String id = "player_stat_" + UUID.randomUUID();

		// create the object with id and provenance information
		PlayerStat playerStat = new PlayerStat(id, provenanceInfo);

		// fill the attributes
		playerStat.setName(getValueFromChildElement(node, "name"));
		playerStat.setYearStart(Integer.parseInt(getValueFromChildElement(node, "year_start")));
		playerStat.setYearEnd(Integer.parseInt(getValueFromChildElement(node, "year_end")));
		playerStat.setPosition(getValueFromChildElement(node, "position"));
		playerStat.setHeight(Float.parseFloat(getValueFromChildElement(node, "height")));
		playerStat.setWeight(Float.parseFloat(getValueFromChildElement(node, "weight")));
		playerStat.setBirthDate(LocalDateTime.parse(getValueFromChildElement(node, "birth_date")));
		playerStat.setCollege(getValueFromChildElement(node, "college"));

		return playerStat;
	}
}
