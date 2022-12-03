package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import java.util.*;
import java.util.stream.Collectors;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;
import de.uni_mannheim.informatik.dws.winter.similarity.list.OverlapSimilarity;

public class TeamsEvaluationRule extends EvaluationRule<Player, Attribute>{

    OverlapSimilarity sim = new OverlapSimilarity();
    @Override
    public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
        if (record1.getTeams() != null && record2.getTeams() != null) {
            List<String> s1 = record1.getTeams().stream().map(String::toLowerCase).collect(Collectors.toList());
            List<String> s2 = record2.getTeams().stream().map(String::toLowerCase).collect(Collectors.toList());

            double similarity = sim.calculate(s1, s2);

            if(similarity >= 0.8) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
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