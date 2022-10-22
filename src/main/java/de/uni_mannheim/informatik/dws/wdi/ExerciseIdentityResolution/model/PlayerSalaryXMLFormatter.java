package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class PlayerSalaryXMLFormatter extends XMLFormatter<PlayerSalary>{
	
	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("players");
	}

	@Override
	public Element createElementFromRecord(PlayerSalary record, Document doc) {
		Element player = doc.createElement("player");

		player.appendChild(createTextElement("name", record.getName(), doc));

		return player;
	}
}
