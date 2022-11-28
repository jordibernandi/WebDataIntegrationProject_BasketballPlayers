package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;

public class BirthPlaceEvaluationRule extends EvaluationRule<Player, Attribute> {

    @Override
    public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
        if(record1.getBirthPlace()== null && record2.getBirthPlace()==null)
            return true;
        else if(record1.getBirthPlace()== null || record2.getBirthPlace()==null)
            return false;
        else
            return record1.getBirthPlace().equals(record2.getBirthPlace());
    }

    @Override
    public boolean isEqual(Player record1, Player record2,
                           Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute)null);
    }

}