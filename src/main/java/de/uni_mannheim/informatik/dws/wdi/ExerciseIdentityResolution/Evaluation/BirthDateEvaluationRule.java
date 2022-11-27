package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * {@link EvaluationRule} for the date of {@link Movie}s. The rule simply
 * compares the year of the dates of two {@link Movie}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class BirthDateEvaluationRule extends EvaluationRule<Player, Attribute> {

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		if(record1.getBirthDate()==null && record2.getBirthDate()==null)
			return true;
		else if(record1.getBirthDate()==null ^ record2.getBirthDate()==null)
			return false;
		else
			return record1.getBirthDate().getYear() == record2.getBirthDate().getYear();
	}

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.datafusion.EvaluationRule#isEqual(java.lang.Object, java.lang.Object, de.uni_mannheim.informatik.wdi.model.Correspondence)
	 */
	@Override
	public boolean isEqual(Player record1, Player record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}
	
}
