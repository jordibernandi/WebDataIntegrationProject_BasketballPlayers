package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.EqualsSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;

public class PlayerHeightComparatorEqual implements Comparator<Player, Attribute> {

    private static final long serialVersionUID = 1L;
    private EqualsSimilarity<String> sim = new EqualsSimilarity<String>();

    private ComparatorLogger comparisonLog;


    @Override
    public double compare(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
        String s1 = String.valueOf(record1.getHeight()).toLowerCase();
        String s2 = String.valueOf(record1.getHeight()).toLowerCase();

        double similarity = sim.calculate(s1, s2);

        if(this.comparisonLog != null){
            this.comparisonLog.setComparatorName(getClass().getName());

            this.comparisonLog.setRecord1Value(String.valueOf(s1));
            this.comparisonLog.setRecord2Value(String.valueOf((s2)));

            this.comparisonLog.setSimilarity(Double.toString(similarity));
        }
        return similarity;
    }

//    @Override
//    public boolean hasMissingValue(PlayerStat record1, PlayerStat record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
//        return Comparator.super.hasMissingValue(record1, record2, schemaCorrespondence);
//    }
//
//    @Override
//    public Attribute getFirstSchemaElement(PlayerStat record) {
//        return Comparator.super.getFirstSchemaElement(record);
//    }
//
//    @Override
//    public Attribute getSecondSchemaElement(PlayerStat record) {
//        return Comparator.super.getSecondSchemaElement(record);
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
//    public String getName(Correspondence<Attribute, Matchable> schemaCorrespondence) {
//        return Comparator.super.getName(schemaCorrespondence);
//    }
}