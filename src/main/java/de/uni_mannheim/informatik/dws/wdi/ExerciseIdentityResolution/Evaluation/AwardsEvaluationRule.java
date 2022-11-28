package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import java.util.*;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;

public class AwardsEvaluationRule extends EvaluationRule<Player, Attribute>{

    @Override
    public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
        Collection awards1 = new ArrayList();
        if (record1.getAwards() != null) {
            for (String p : record1.getAwards()) {

            	awards1.add(p);
            }
        }
        Collection awards2 = new ArrayList();
        if (record2.getAwards() != null) {
            for (String p : record2.getAwards()) {
            	awards2.add(p);
            }
        }


        awards1.retainAll( awards2 );
        return awards1.size()>0;
    }

    /* (non-Javadoc)
     * @see de.uni_mannheim.informatik.wdi.datafusion.EvaluationRule#isEqual(java.lang.Object, java.lang.Object, de.uni_mannheim.informatik.wdi.model.Correspondence)
     */
    @Override
    public boolean isEqual(Player record1, Player record2,
                           Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute)null);
    }
}