package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;

public class PlayerNameComparatorJaccardTokenizer implements Comparator<Player, Attribute> {

	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity sim = new LevenshteinSimilarity();

	private ComparatorLogger comparisonLog;

	@Override
	public double compare(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		String s1 = String.valueOf(record1.getName()).toLowerCase();
		String s2 = String.valueOf(record2.getName()).toLowerCase();

		System.out.println(record1.getIdentifier());

		if(record1.getIdentifier() == "player_stat_id_2196") {
			System.out.println("HELLO");
			System.out.println(record1.getIdentifier());
		}

		double similarity = sim.calculate(s1, s2);

		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());

			this.comparisonLog.setRecord1Value(s1);
			this.comparisonLog.setRecord2Value(s2);

			this.comparisonLog.setSimilarity(Double.toString(similarity));
		}
		return similarity;
	}

//	@Override
//	public boolean hasMissingValue(PlayerStat record1, PlayerStat record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
//		return Comparator.super.hasMissingValue(record1, record2, schemaCorrespondence);
//	}



	@Override
	public ComparatorLogger getComparisonLog() {
		return this.comparisonLog;
	}

	@Override
	public void setComparisonLog(ComparatorLogger comparatorLog) {
		this.comparisonLog = comparatorLog;
	}

}
