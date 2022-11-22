/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@link Comparator} for {@link Movie}s based on the
 * {@link Movie#getDirector()} values, and their {@link LevenshteinSimilarity}
 * similarity.
 *
 * @author Oliver Lehmberg (oli@dwslab.de)
 *
 */
public class PlayerBirthDateComparatorLevenshtein implements Comparator<Player, Attribute> {

	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity sim = new LevenshteinSimilarity();

	private ComparatorLogger comparisonLog;



	@Override
	public double compare(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		LocalDateTime record1BirthDate = record1.getBirthDate();
        LocalDateTime record2BirthDate = record2.getBirthDate();

		if(record1BirthDate != null && record2BirthDate != null) {
			String s1 = record1BirthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String s2 = record2BirthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));;

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

//	@Override
//	public boolean hasMissingValue(PlayerStat record1, PlayerStat record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
//		return Comparator.super.hasMissingValue(record1, record2, schemaCorrespondence);
//	}
//
//	@Override
//	public Attribute getFirstSchemaElement(PlayerStat record) {
//		return Comparator.super.getFirstSchemaElement(record);
//	}
//
//	@Override
//	public Attribute getSecondSchemaElement(PlayerStat record) {
//		return Comparator.super.getSecondSchemaElement(record);
//	}

	@Override
    public ComparatorLogger getComparisonLog() {
        return this.comparisonLog;
    }

    @Override
    public void setComparisonLog(ComparatorLogger comparatorLog) {
    	this.comparisonLog = comparatorLog;
    }


//	@Override
//	public String getName(Correspondence<Attribute, Matchable> schemaCorrespondence) {
//		return Comparator.super.getName(schemaCorrespondence);
//	}
}
