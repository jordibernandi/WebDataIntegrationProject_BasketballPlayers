package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;

public class PlayerWeightComparatorLevenshtein implements Comparator<Player, Attribute> {
	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity sim = new LevenshteinSimilarity();

	private ComparatorLogger comparisonLog;


	@Override
	public double compare(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		float s1 = record1.getWeight();
		float s2 = record2.getWeight();

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
