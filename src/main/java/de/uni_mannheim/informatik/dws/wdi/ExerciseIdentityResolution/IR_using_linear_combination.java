package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerNameComparatorEqual;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerNameComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerNameComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.*;
import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.MovieBlockingKeyByTitleGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieDateComparator2Years;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieTitleComparatorJaccard;
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
		gsTestPlayerStatDBpedia.loadFromCSVFile(new File("data/goldstandard/gs_player_stat_2_dbpedia.csv"));

//		System.out.println("*\n*\tLoading gold standard for Real Market Players to FIFA \n*");
//		LinearCombinationMatchingRule<Player, Attribute> matchingRuleRealFifa = new LinearCombinationMatchingRule<>(0.7);
//		MatchingGoldStandard gsTestRealFifa = new MatchingGoldStandard();
//		matchingRuleRealFifa.activateDebugReport("data/output/debugResultsMatchingRuleRealFifa.csv", 1000, gsTestRealFifa);
//		gsTestRealFifa.loadFromCSVFile(new File("data/goldstandard/real_market_2_fifa.csv"));
//
//		System.out.println("*\n*\tLoading gold standard for Prediction to FIFA\n*");
//		LinearCombinationMatchingRule<Player, Attribute> matchingRulePredFifa = new LinearCombinationMatchingRule<>(0.75);
//		MatchingGoldStandard gsTestPredFifa = new MatchingGoldStandard();
//		matchingRulePredFifa.activateDebugReport("data/output/debugResultsMatchingRulePredFifas.csv", 1000, gsTestPredFifa);
//		gsTestPredFifa.loadFromCSVFile(new File("data/goldstandard/prediction_2_fifa.csv"));

		//added comparators for RealPred
		matchingRulePlayerStatDBpedia.addComparator(new PlayerNameComparatorEqual(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerNameComparatorJaccard(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerNameComparatorLevenshtein(), 1);
//		matchingRuleRealPred.addComparator(new PlayerNationalityComparatorJaccard(), 0.10);
//		matchingRuleRealPred.addComparator(new PlayerBirthDateComparatorEqual(), 0.50);
//
//		//added comparators for RealFifa
//		matchingRuleRealFifa.addComparator(new PlayerNameShortComparatorJaccard(), 0.40);
//		matchingRuleRealFifa.addComparator(new PlayerClubComparatorNGramJaccard(), 0.20);
//		matchingRuleRealFifa.addComparator(new PlayerNationalityComparatorJaccard(), 0.35);
//		matchingRuleRealFifa.addComparator(new PlayerKitNumberComparatorEqual(), 0.05);
//
//		//added comparators for PredFifa
//		matchingRulePredFifa.addComparator(new PlayerNameShortComparatorJaccard(), 0.40);
//		matchingRulePredFifa.addComparator(new PlayerNationalityComparatorJaccard(), 0.30);
//		matchingRulePredFifa.addComparator(new PlayerClubComparatorNGramJaccard(), 0.30);
//		//matchingRulePredFifa.addComparator(new PlayerPositionComparatorJaccard(), 0.05);
//
//		// Initialize Matching Engines
		MatchingEngine<Player, Attribute> enginePlayerStatDBpedia = new MatchingEngine<>();
//		MatchingEngine<Player, Attribute> engineRealFifa = new MatchingEngine<>();
//		MatchingEngine<Player, Attribute> enginePredFifa = new MatchingEngine<>();
//
//		// create a blocker (blocking strategy)
		NoBlocker<Player, Attribute> blocker = new NoBlocker<>();
//		SortedNeighbourhoodBlocker<Movie, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new MovieBlockingKeyByTitleGenerator(), 1);
//		StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingKeyByNationalityGenerator());
//
//		// Execute the matchings
		Processable<Correspondence<Player, Attribute>> correspondencesPlayerStatDBpedia = enginePlayerStatDBpedia.runIdentityResolution(
				dataPlayerStat, dataPlayerDBpedia, null, matchingRulePlayerStatDBpedia, blocker);
//		Processable<Correspondence<Player, Attribute>> correspondencesRealFifa = engineRealFifa.runIdentityResolution(
//				dataRealPlayers, dataFifaPlayers, null, matchingRuleRealFifa, blocker);
//		Processable<Correspondence<Player, Attribute>> correspondencesPredFifa = enginePredFifa.runIdentityResolution(
//				dataPredictionPlayers, dataFifaPlayers , null, matchingRulePredFifa, blocker);

		correspondencesPlayerStatDBpedia = enginePlayerStatDBpedia.getTopKInstanceCorrespondences(correspondencesPlayerStatDBpedia, 1, 0.8);
//		correspondencesRealFifa = engineRealPred.getTopKInstanceCorrespondences(correspondencesRealFifa, 1, 0.75);
//		correspondencesPredFifa = engineRealPred.getTopKInstanceCorrespondences(correspondencesPredFifa, 1, 0.75);
//
//		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/correspondences/player_stat_2_dbpedia_correspondences.csv"), correspondencesPlayerStatDBpedia);
//		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/correspondences/real_2_fifa_correspondences.csv"), correspondencesRealFifa);
//		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/correspondences/prediction_2_fifa_correspondences.csv"), correspondencesPredFifa);
//
//
		System.out.println("*\n*\tEvaluating result for Player Stat to DBpedia \n*");
		// evaluate your results
		MatchingEvaluator<Player, Attribute> evaluatorPlayerStatDBpedia = new MatchingEvaluator<Player, Attribute>();
		Performance perfTestPlayerStatDBpedia = evaluatorPlayerStatDBpedia.evaluateMatching(correspondencesPlayerStatDBpedia,
				gsTestPlayerStatDBpedia);

//		System.out.println("*\n*\tEvaluating result for Real to FIFA\n*");
//		MatchingEvaluator<Player, Attribute> evaluatorRealFifa = new MatchingEvaluator<Player, Attribute>();
//		Performance perfTestRealFifa = evaluatorRealFifa.evaluateMatching(correspondencesRealFifa,
//				gsTestRealFifa);
//
//		System.out.println("*\n*\tEvaluating result for Prediction to FIFA\n*");
//		MatchingEvaluator<Player, Attribute> evaluatorPredFifa = new MatchingEvaluator<Player, Attribute>();
//		Performance perfTestPredFifa = evaluatorPredFifa.evaluateMatching(correspondencesPredFifa,
//				gsTestPredFifa);
//
		// print the evaluation result
		System.out.println("Players - Stat <-> Players - DBpedia");
		System.out.println(String.format(
				"Precision: %.4f",perfTestPlayerStatDBpedia.getPrecision()));
		System.out.println(String.format(
				"Recall: %.4f",	perfTestPlayerStatDBpedia.getRecall()));
		System.out.println(String.format(
				"F1: %.4f",perfTestPlayerStatDBpedia.getF1()));

//		System.out.println("Players - Real Market <-> Players - Fifa");
//		System.out.println(String.format(
//				"Precision: %.4f",perfTestRealFifa.getPrecision()));
//		System.out.println(String.format(
//				"Recall: %.4f",	perfTestRealFifa.getRecall()));
//		System.out.println(String.format(
//				"F1: %.4f",perfTestRealFifa.getF1()));
//
//		System.out.println("Players - Predicted Price <-> Players - Fifa");
//		System.out.println(String.format(
//				"Precision: %.4f",perfTestPredFifa.getPrecision()));
//		System.out.println(String.format(
//				"Recall: %.4f",	perfTestPredFifa.getRecall()));
//		System.out.println(String.format(
//				"F1: %.4f",perfTestPredFifa.getF1()));
    }
}
