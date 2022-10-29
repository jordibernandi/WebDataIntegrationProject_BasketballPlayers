package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.util.UUID;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class PlayerSalaryXMLReader extends XMLMatchableReader<PlayerSalary, Attribute>  {

    @Override
    protected void initialiseDataset(DataSet<PlayerSalary, Attribute> dataset) {
        super.initialiseDataset(dataset);

    }

    @Override
    public PlayerSalary createModelFromElement(Node node, String provenanceInfo) {
        String id = "player_salary_" + UUID.randomUUID();


        // create the object with id and provenance information
        PlayerSalary playerSalary = new PlayerSalary(id, provenanceInfo);

        // fill the attributes
        playerSalary.setRegisterValue(Integer.parseInt(getValueFromChildElement(node, "registerValue")));
        playerSalary.setName(getValueFromChildElement(node, "name"));
        playerSalary.setSalary(Double.parseDouble(getValueFromChildElement(node, "salary")));
        playerSalary.setStartYear(Integer.parseInt(getValueFromChildElement(node, "startYear")));
        playerSalary.setEndYear(Integer.parseInt(getValueFromChildElement(node, "endYear")));
        playerSalary.setTeam(getValueFromChildElement(node, "team"));
        playerSalary.setFullTeamName(getValueFromChildElement(node, "fullTeamName"));

        return playerSalary;
	}

}
