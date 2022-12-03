package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import java.util.*;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;

public class TeamsEvaluationRule extends EvaluationRule<Player, Attribute>{

    @Override
    public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
        Set<String> player1 = new HashSet<>();
        if (record1.getTeams() != null) {
            for (String p : record1.getTeams()) {

                player1.add(p);
            }
        }
        Set<String> player2 = new HashSet<>();
        if (record2.getTeams() != null) {
            for (String p : record2.getTeams()) {
                player2.add(p);
            }
        }

        return player1.containsAll(player2) && player2.containsAll(player1);
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