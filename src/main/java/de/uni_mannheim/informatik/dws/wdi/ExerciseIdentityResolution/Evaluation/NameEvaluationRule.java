package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;
import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;

public class NameEvaluationRule extends EvaluationRule<Player, Attribute> {

    SimilarityMeasure<String> sim = new TokenizingJaccardSimilarity();

    @Override
    public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
        return sim.calculate(record1.getName(), record2.getName()) == 1.0;
    }

    @Override
    public boolean isEqual(Player record1, Player record2,
                           Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute)null);
    }

}