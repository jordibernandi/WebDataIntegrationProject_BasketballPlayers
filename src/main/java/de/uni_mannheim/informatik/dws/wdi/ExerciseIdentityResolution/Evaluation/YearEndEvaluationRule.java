package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;

public class YearEndEvaluationRule extends EvaluationRule<Player, Attribute> {
    @Override
    public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
        if(record1.getYearEnd() >= (record2.getYearEnd() - 1) && record1.getYearEnd() <= record2.getYearEnd() + 1)
            return true;
        else
            return false;
    }

    @Override
    public boolean isEqual(Player record1, Player record2,
                           Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute)null);
    }
}