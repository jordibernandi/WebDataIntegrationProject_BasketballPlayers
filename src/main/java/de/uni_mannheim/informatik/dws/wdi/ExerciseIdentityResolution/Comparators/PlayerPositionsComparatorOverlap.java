package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;
import de.uni_mannheim.informatik.dws.winter.similarity.list.OverlapSimilarity;
import de.uni_mannheim.informatik.dws.winter.utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerPositionsComparatorOverlap implements Comparator<Player, Attribute> {
    private static final long serialVersionUID = 1L;
    private OverlapSimilarity sim = new OverlapSimilarity();

    private ComparatorLogger comparisonLog;

    @Override
    public double compare(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
        if (record1.getPositions() != null && record2.getPositions() != null) {
            List<String> s1 = record1.getPositions().stream().map(String::toLowerCase).collect(Collectors.toList());
            List<String> s2 = record2.getPositions().stream().map(String::toLowerCase).collect(Collectors.toList());

            double similarity = sim.calculate(s1, s2);

            if (this.comparisonLog != null) {
                this.comparisonLog.setComparatorName(getClass().getName());

                this.comparisonLog.setRecord1Value(StringUtils.join(s1, "|"));
                this.comparisonLog.setRecord2Value(StringUtils.join(s2, "|"));

                this.comparisonLog.setSimilarity(Double.toString(similarity));
            }
            return similarity;
        } else {
            return 0;
        }
    }

    @Override
    public ComparatorLogger getComparisonLog() {
        return this.comparisonLog;
    }

    @Override
    public void setComparisonLog(ComparatorLogger comparatorLog) {
        this.comparisonLog = comparatorLog;
    }
}
