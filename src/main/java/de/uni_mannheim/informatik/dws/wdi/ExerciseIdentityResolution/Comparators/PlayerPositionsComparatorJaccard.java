package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;

import java.util.List;

public class PlayerPositionsComparatorJaccard implements Comparator<Player, Attribute> {

    private static final long serialVersionUID = 1L;
    private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();

    private ComparatorLogger comparisonLog;

    @Override
    public double compare(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
        List<String> s1 = record1.getPositions();
        List<String> s2L = record2.getPositions();
        if (s2L != null && s1 != null) {
            String s2 = s2L.get(0);
            for (int posNumb = 0; posNumb < s1.size(); posNumb++) {
                String chars = "";
                for (int i = 0; i < s1.get(posNumb).length(); i++) {
                    if (Character.isUpperCase(s1.get(posNumb).charAt(i))) {
                        chars = chars + s1.get(posNumb).charAt(i);
                    }
                }
                s1.set(posNumb, chars);
            }
            double maxVal = 0;
            String s1S = "";
            for (int i = 0; i < s1.size(); i++) {
                double similarity = sim.calculate(s2, s1.get(i));
                if (similarity > maxVal) {
                    maxVal = similarity;
                    s1S = s1.get(i);
                }
            }

            if (this.comparisonLog != null) {
                this.comparisonLog.setComparatorName(getClass().getName());

                this.comparisonLog.setRecord1Value(s1S);
                this.comparisonLog.setRecord2Value(s2);

                this.comparisonLog.setSimilarity(Double.toString(maxVal));
            }

            return maxVal;
        } else{
            return 0;
        }
    }

//    @Override
//    public boolean hasMissingValue(PlayerStat record1, PlayerStat record2, Correspondence<PlayerDBpedia, Matchable> schemaCorrespondence) {
//        return Comparator.super.hasMissingValue(record1, record2, schemaCorrespondence);
//    }


    @Override
    public ComparatorLogger getComparisonLog() {
        return Comparator.super.getComparisonLog();
    }

    @Override
    public void setComparisonLog(ComparatorLogger comparatorLog) {
        Comparator.super.setComparisonLog(comparatorLog);
    }

//    @Override
//    public String getName(Correspondence<PlayerDBpedia, Matchable> schemaCorrespondence) {
//        return Comparator.super.getName(schemaCorrespondence);
//    }
}
