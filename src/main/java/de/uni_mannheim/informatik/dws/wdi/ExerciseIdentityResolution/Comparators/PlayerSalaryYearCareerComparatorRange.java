package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Salary;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.EqualsSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;
import org.joda.time.format.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PlayerSalaryYearCareerComparatorRange implements Comparator<Player, Attribute> {

    private static final long serialVersionUID = 1L;
    private YearSimilarity sim = new YearSimilarity(5);

    private ComparatorLogger comparisonLog;

    @Override
    public double compare(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {

        int yearStart = record1.getYearStart();
        int yearEnd = record1.getYearEnd();
        List<Salary> salaries = record2.getSalaries();
        int seasonStartYear = salaries.get(0).getSeasonStartYear();

        double similarity = 0;

        if(seasonStartYear >= yearStart && seasonStartYear <= yearEnd) {
            similarity = 1;
        } else if(seasonStartYear < yearStart) {
            LocalDateTime ssy = LocalDateTime.of(seasonStartYear, 01, 01, 01,01);
            LocalDateTime ys = LocalDateTime.of(yearStart, 01, 01,01,01);
            similarity = sim.calculate(ssy, ys);
        } else if(seasonStartYear > yearEnd){
            LocalDateTime ssy = LocalDateTime.of(seasonStartYear, 01, 01, 01,01);
            LocalDateTime ye = LocalDateTime.of(yearEnd, 01, 01,01,01);
            similarity = sim.calculate(ssy, ye);
        } else {
            similarity = 0;
        }

        if (this.comparisonLog != null) {
            this.comparisonLog.setComparatorName(getClass().getName());

            this.comparisonLog.setRecord1Value(yearStart + "-" + yearEnd);
            this.comparisonLog.setRecord2Value(Integer.toString(seasonStartYear));

            this.comparisonLog.setSimilarity(Double.toString(similarity));
        }
        return similarity;
    }

//    @Override
//    public boolean hasMissingValue(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
//        return Comparator.super.hasMissingValue(record1, record2, schemaCorrespondence);
//    }

    @Override
    public ComparatorLogger getComparisonLog() {
        return this.comparisonLog;
    }

    @Override
    public void setComparisonLog(ComparatorLogger comparatorLog) {
        this.comparisonLog = comparatorLog;
    }
}