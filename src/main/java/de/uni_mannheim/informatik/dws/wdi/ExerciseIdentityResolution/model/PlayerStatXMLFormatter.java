package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Actor}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class PlayerStatXMLFormatter extends XMLFormatter<Playerstat> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("playerstats");
	}

	@Override
	public Element createElementFromRecord(Playerstat record, Document doc) {
		Element playerstat = doc.createElement("playerstat");

		actor.appendChild(createTextElement("name", record.getName(), doc));

		return playerstat;
	}

}
