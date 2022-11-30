package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class InjuryXMLFormatter extends XMLFormatter<Injury> {
    @Override
    public Element createRootElement(Document doc) {
        return doc.createElement("injuries");
    }

    @Override
    public Element createElementFromRecord(Injury record, Document doc) {
        Element injury = doc.createElement("injury");

        if(record.getInjuryDate()!=null) {
            injury.appendChild(createTextElement("injuryDate", record.getInjuryDate().toString(), doc));
        }
        if(record.getInjuryNote()!=null) {
            injury.appendChild(createTextElement("injuryNote", record.getInjuryNote(), doc));
        }

        return injury;
    }
}
