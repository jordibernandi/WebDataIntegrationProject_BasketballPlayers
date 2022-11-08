package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.EqualsSimilarity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.PlayerDBpedia;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.PlayerStat;

public class PlayerBirthdateComparatorEqual implements Comparator<PlayerStat, Attribute> {

    private static final long serialVersionUID = 1L;
    private EqualsSimilarity<String> sim = new EqualsSimilarity<String>();

    private ComparatorLogger comparisonLog;





    @Override
    public double compare(PlayerStat record1, PlayerStat record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
        LocalDate record1BirthDate = LocalDate.from(record1.getBirthDate());
        LocalDate record2BirthDate = LocalDate.from(record2.getBirthDate());

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
    public boolean hasMissingValue(PlayerStat record1, PlayerStat record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return Comparator.super.hasMissingValue(record1, record2, schemaCorrespondence);
    }



    @Override
    public ComparatorLogger getComparisonLog() {
        return Comparator.super.getComparisonLog();
    }



    @Override
    public String getName(Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return Comparator.super.getName(schemaCorrespondence);
    }

    @Override
    public void setComparisonLog(ComparatorLogger comparatorLog) {
        Comparator.super.setComparisonLog(comparatorLog);
    }
}