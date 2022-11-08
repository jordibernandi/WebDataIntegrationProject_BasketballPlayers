package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.PlayerDBpedia;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.PlayerStat;

public class PlayerTeamsComparatorJaccard implements Comparator<PlayerDBpedia, PlayerDBpedia> {
    private static final long serialVersionUID = 1L;
    private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();

    private ComparatorLogger comparisonLog;
    @Override
    public double compare(PlayerDBpedia record1, PlayerSalary record2, Correspondence<PlayerDBpedia, Matchable> schemaCorrespondence) {
        int s1 = record1.getTeams();
        int s2 = record2.getTeam();

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
    public boolean hasMissingValue(PlayerDBpedia record1, PlayerSalary record2, Correspondence<PlayerDBpedia, Matchable> schemaCorrespondence) {
        return Comparator.super.hasMissingValue(record1, record2, schemaCorrespondence);
    }

    @Override
    public PlayerDBpedia getFirstSchemaElement(PlayerDBpedia record) {
        return Comparator.super.getFirstSchemaElement(record);
    }

    @Override
    public PlayerDBpedia getSecondSchemaElement(PlayerDBpedia record) {
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
