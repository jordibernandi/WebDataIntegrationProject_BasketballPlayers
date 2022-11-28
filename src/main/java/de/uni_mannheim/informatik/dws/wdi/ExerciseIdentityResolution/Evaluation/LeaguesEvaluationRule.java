package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import java.util.*;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;

public class LeaguesEvaluationRule extends EvaluationRule<Player, Attribute>{

    @Override
    public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
        Collection leagues1 = new ArrayList();
        if (record1.getLeagues() != null) {
            for (String p : record1.getLeagues()) {

            	leagues1.add(p);
            }
        }
        //Set<String> positions2 = new HashSet<>();
        Collection leagues2 = new ArrayList();
        if (record2.getLeagues() != null) {
            for (String p : record2.getLeagues()) {
            	leagues2.add(p);
            }
        }


        leagues1.retainAll( leagues2 );
        //System.out.println( positions1.size() );
        return leagues1.size()>0;
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