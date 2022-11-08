package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.PlayerDBpedia;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.PlayerStat;

public class PlayerHeightComparatorJaccard implements Comparator<PlayerStat, Attribute> {
    private static final long serialVersionUID = 1L;
    private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();

    private ComparatorLogger comparisonLog;


    @Override
    public double compare(PlayerStat record1, PlayerStat record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
        String s1 = String.valueOf(record1.getHeight()).toLowerCase();
        String s2 = String.valueOf(record2.getHeight()).toLowerCase();

        double similarity = sim.calculate(s1, s2);

        if(this.comparisonLog != null){
            this.comparisonLog.setComparatorName(getClass().getName());

            this.comparisonLog.setRecord1Value(s1);
            this.comparisonLog.setRecord2Value(s2);

            this.comparisonLog.setSimilarity(Double.toString(similarity));
        }

        return similarity;
    }

    @Override
    public boolean hasMissingValue(PlayerStat record1, PlayerStat record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return Comparator.super.hasMissingValue(record1, record2, schemaCorrespondence);
    }


    @Override
    public ComparatorLogger getComparisonLog() {
        return Comparator.super.getComparisonLog();
    }

    @Override
    public void setComparisonLog(ComparatorLogger comparatorLog) {
        Comparator.super.setComparisonLog(comparatorLog);
    }

    @Override
    public String getName(Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return Comparator.super.getName(schemaCorrespondence);
    }
}
