package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.*;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.PlayerBlockingByKeyNameGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.*;
import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.MaximumBipartiteMatchingAlgorithm;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.Blocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class IR_using_linear_combination 
{
	/*
	 * Logging Options:
	 * 		default: 	level INFO	- console
	 * 		trace:		level TRACE     - console
	 * 		infoFile:	level INFO	- console/file
	 * 		traceFile:	level TRACE	- console/file
	 *  
	 * To set the log level to trace and write the log to winter.log and console, 
	 * activate the "traceFile" logger as follows:
	 *     private static final Logger logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final Logger logger = WinterLogManager.activateLogger("default");

    public static void main( String[] args ) throws Exception
    {
		// loading data
		logger.info("*\tLoading datasets\t*");
		HashedDataSet<Player, Attribute> dataPlayerStat = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/player_stat.xml"), "/players/player", dataPlayerStat);
		HashedDataSet<Player, Attribute> dataPlayerDBpedia = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/player_dbpedia.xml"), "/players/player", dataPlayerDBpedia);
		HashedDataSet<Player, Attribute> dataPlayerSalary = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/player_salary.xml"), "/players/player", dataPlayerSalary);
		HashedDataSet<Player, Attribute> dataPlayerInjury = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/player_injury.xml"), "/players/player", dataPlayerInjury);

		// load the gold standard (test set)
		System.out.println("*\n*\tLoading gold standard for Player Stat to DBpedia \n*");
		LinearCombinationMatchingRule<Player, Attribute> matchingRulePlayerStatDBpedia = new LinearCombinationMatchingRule<>(0.8);
		MatchingGoldStandard gsTestPlayerStatDBpedia = new MatchingGoldStandard();
		matchingRulePlayerStatDBpedia.activateDebugReport("data/output/debugResultsMatchingRulePlayerStatDBpedia.csv", 1000, gsTestPlayerStatDBpedia);
		gsTestPlayerStatDBpedia.loadFromCSVFile(new File("data/goldstandard/gs_player_stat_2_dbpedia_test.csv"));

		System.out.println("*\n*\tLoading gold standard for Player Stat to Salary \n*");
		LinearCombinationMatchingRule<Player, Attribute> matchingRulePlayerStatSalary = new LinearCombinationMatchingRule<>(0.7);
		MatchingGoldStandard gsTestPlayerStatSalary = new MatchingGoldStandard();
		matchingRulePlayerStatSalary.activateDebugReport("data/output/debugResultsMatchingRulePlayerStatSalary.csv", 1000, gsTestPlayerStatSalary);
		gsTestPlayerStatSalary.loadFromCSVFile(new File("data/goldstandard/gs_player_stat_2_salary_test.csv"));

		System.out.println("*\n*\tLoading gold standard for Player Stat to Injury \n*");
		LinearCombinationMatchingRule<Player, Attribute> matchingRulePlayerStatInjury = new LinearCombinationMatchingRule<>(0.75);
		MatchingGoldStandard gsTestPlayerStatInjury = new MatchingGoldStandard();
		matchingRulePlayerStatInjury.activateDebugReport("data/output/debugResultsMatchingRulePlayerStatInjury.csv", 1000, gsTestPlayerStatInjury);
		gsTestPlayerStatInjury.loadFromCSVFile(new File("data/goldstandard/gs_player_stat_2_injury_test.csv"));

		//added comparators for StatDBpedia
		matchingRulePlayerStatDBpedia.addComparator(new PlayerNameComparatorLevenshtein(), 0.4);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerBirthDateComparatorEqual(), 0.4);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerHeightComparatorPercentageSim(), 0.1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerWeightComparatorPercentageSim(), 0.1);
		
		//added comparators for StatSalary
		matchingRulePlayerStatSalary.addComparator(new PlayerNameComparatorJaccard(), 0.5);
		matchingRulePlayerStatSalary.addComparator(new PlayerSalaryYearCareerComparatorRange(), 0.5);

		//added comparators for StatInjury
		matchingRulePlayerStatInjury.addComparator(new PlayerNameComparatorJaccard(), 0.4);
		matchingRulePlayerStatInjury.addComparator(new PlayerPositionsComparatorOverlap(), 0.2);
		matchingRulePlayerStatInjury.addComparator(new PlayerInjuryYearCareerComparatorRange(), 0.4);

		// Initialize Matching Engines
		MatchingEngine<Player, Attribute> enginePlayerStatDBpedia = new MatchingEngine<>();
		MatchingEngine<Player, Attribute> enginePlayerStatSalary = new MatchingEngine<>();
		MatchingEngine<Player, Attribute> enginePlayerStatInjury = new MatchingEngine<>();

		// create a blocker (blocking strategy)
//		NoBlocker<Player, Attribute> blocker = new NoBlocker<>();
//		SortedNeighbourhoodBlocker<Player, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new PlayerBlockingByKeyNameGenerator(), 500);
		StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingByKeyNameGenerator());

		// Execute the matchings
		Processable<Correspondence<Player, Attribute>> correspondencesPlayerStatDBpedia = enginePlayerStatDBpedia.runIdentityResolution(
				dataPlayerStat, dataPlayerDBpedia, null, matchingRulePlayerStatDBpedia, blocker);
		Processable<Correspondence<Player, Attribute>> correspondencesPlayerStatSalary = enginePlayerStatSalary.runIdentityResolution(
				dataPlayerStat, dataPlayerSalary, null, matchingRulePlayerStatSalary, blocker);
		Processable<Correspondence<Player, Attribute>> correspondencesPlayerStatInjury = enginePlayerStatInjury.runIdentityResolution(
				dataPlayerStat, dataPlayerInjury , null, matchingRulePlayerStatInjury, blocker);

//		correspondencesPlayerStatDBpedia = enginePlayerStatDBpedia.getTopKInstanceCorrespondences(correspondencesPlayerStatDBpedia, 1, 0.8);
//		correspondencesPlayerStatSalary = enginePlayerStatSalary.getTopKInstanceCorrespondences(correspondencesPlayerStatSalary, 1, 0.75);
//		correspondencesPlayerStatInjury = enginePlayerStatInjury.getTopKInstanceCorrespondences(correspondencesPlayerStatInjury, 1, 0.75);

		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/correspondences/player_stat_2_dbpedia_correspondences.csv"), correspondencesPlayerStatDBpedia);
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/correspondences/player_stat_2_salary_correspondences.csv"), correspondencesPlayerStatSalary);
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/correspondences/player_stat_2_injury_correspondences.csv"), correspondencesPlayerStatInjury);

		// evaluate your results
				System.out.println("*\n*\tEvaluating result for Player Stat to DBpedia \n*");
		MatchingEvaluator<Player, Attribute> evaluatorPlayerStatDBpedia = new MatchingEvaluator<Player, Attribute>();
		Performance perfTestPlayerStatDBpedia = evaluatorPlayerStatDBpedia.evaluateMatching(correspondencesPlayerStatDBpedia,
				gsTestPlayerStatDBpedia);

		System.out.println("");

		System.out.println("*\n*\tEvaluating result for Player Stat to Salary \n*");
		MatchingEvaluator<Player, Attribute> evaluatorPlayerStatSalary = new MatchingEvaluator<Player, Attribute>();
		Performance perfTestPlayerStatSalary = evaluatorPlayerStatSalary.evaluateMatching(correspondencesPlayerStatSalary,
				gsTestPlayerStatSalary);

		System.out.println("");

		System.out.println("*\n*\tEvaluating result for Player Stat to Injury \n*");
		MatchingEvaluator<Player, Attribute> evaluatorPlayerStatInjury = new MatchingEvaluator<Player, Attribute>();
		Performance perfTestPlayerStatInjury = evaluatorPlayerStatInjury.evaluateMatching(correspondencesPlayerStatInjury,
				gsTestPlayerStatInjury);

		System.out.println("");

		// print the evaluation result
		System.out.println("Players - Stat <-> Players - DBpedia");
		System.out.println(String.format(
				"Precision: %.4f",perfTestPlayerStatDBpedia.getPrecision()));
		System.out.println(String.format(
				"Recall: %.4f",	perfTestPlayerStatDBpedia.getRecall()));
		System.out.println(String.format(
				"F1: %.4f",perfTestPlayerStatDBpedia.getF1()));

		System.out.println("Players - Stat <-> Players - Salary");
		System.out.println(String.format(
				"Precision: %.4f",perfTestPlayerStatSalary.getPrecision()));
		System.out.println(String.format(
				"Recall: %.4f",	perfTestPlayerStatSalary.getRecall()));
		System.out.println(String.format(
				"F1: %.4f",perfTestPlayerStatSalary.getF1()));

		System.out.println("Players - Stat <-> Players - Injury");
		System.out.println(String.format(
				"Precision: %.4f",perfTestPlayerStatInjury.getPrecision()));
		System.out.println(String.format(
				"Recall: %.4f",	perfTestPlayerStatInjury.getRecall()));
		System.out.println(String.format(
				"F1: %.4f",perfTestPlayerStatInjury.getF1()));
    }
}
