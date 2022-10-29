package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class PlayerStatXMLFormatter extends XMLFormatter<PlayerStat> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("playerstats");
	}

	@Override
	public Element createElementFromRecord(PlayerStat record, Document doc) {
		Element playerStat = doc.createElement("playerStat");

		playerStat.appendChild(createTextElement("name", record.getName(), doc));

		return playerStat;
	}

}
