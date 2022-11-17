package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.EqualsSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlayerBirthdateComparatorEqual implements Comparator<Player, Attribute> {

	private static final long serialVersionUID = 1L;
	private YearSimilarity sim = new YearSimilarity(10);
	
	private ComparatorLogger comparisonLog;


    @Override
    public double compare(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
        LocalDateTime record1BirthDate = record1.getBirthDate();
        LocalDateTime record2BirthDate = record2.getBirthDate();
        double similarity = sim.calculate(record1.getBirthDate(), record2.getBirthDate());
        
        if(record1BirthDate != null && record2BirthDate != null) {
            String s1 = record1BirthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String s2 = record2BirthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            
        	
    		if(this.comparisonLog != null){
    			this.comparisonLog.setComparatorName(getClass().getName());
    		
    			this.comparisonLog.setRecord1Value(record1.getBirthDate().toString());
    			this.comparisonLog.setRecord2Value(record2.getBirthDate().toString());
        	
    			this.comparisonLog.setSimilarity(Double.toString(similarity));
    		}
    		
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




//    @Override
//    public String getName(Correspondence<Attribute, Matchable> schemaCorrespondence) {
//        return Comparator.super.getName(schemaCorrespondence);
//    }
}