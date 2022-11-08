package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.PlayerDBpedia;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.PlayerStat;

public class PlayerBirthdateComparatorJaccard implements Comparator<PlayerStat, PlayerDBpedia> {
    private static final long serialVersionUID = 1L;
    private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();

    private ComparatorLogger comparisonLog;
    @Override
    public double compare(PlayerStat record1, PlayerStat record2, Correspondence<PlayerDBpedia, Matchable> schemaCorrespondence) {
    	LocalDate record1BirthDate = record1.getBirthDate();
        LocalDate record2BirthDate = record2.getBirthDate();

        if(record1BirthDate != null && record2BirthDate != null) {
            String s1 = record1BirthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String s2 = record2BirthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            double similarity = sim.calculate(s1, s2);

            if(this.comparisonLog != null){
                this.comparisonLog.setComparatorName(getClass().getName());

                this.comparisonLog.setRecord1Value(s1);
                this.comparisonLog.setRecord2Value(s2);

                this.comparisonLog.setSimilarity(Double.toString(similarity));
            }

            return similarity;
        } else {
            return 0;
        }
    }

    @Override
    public boolean hasMissingValue(PlayerStat record1, PlayerStat record2, Correspondence<PlayerDBpedia, Matchable> schemaCorrespondence) {
        return Comparator.super.hasMissingValue(record1, record2, schemaCorrespondence);
    }

    @Override
    public PlayerDBpedia getFirstSchemaElement(PlayerStat record) {
        return Comparator.super.getFirstSchemaElement(record);
    }

    @Override
    public PlayerDBpedia getSecondSchemaElement(PlayerStat record) {
        return Comparator.super.getSecondSchemaElement(record);
    }

    @Override
    public ComparatorLogger getComparisonLog() {
        return Comparator.super.getComparisonLog();
    }

    @Override
    public void setComparisonLog(ComparatorLogger comparatorLog) {
        Comparator.super.setComparisonLog(comparatorLog);
    }
}
