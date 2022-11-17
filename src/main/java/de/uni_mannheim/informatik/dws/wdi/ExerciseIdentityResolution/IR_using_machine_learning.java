package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.*;
import org.slf4j.Logger;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.MovieBlockingKeyByDecadeGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.MovieBlockingKeyByTitleGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.MovieBlockingKeyByYearGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.PlayerBlockingByKeyNameGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieDateComparator10Years;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieDateComparator2Years;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieDirectorComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieDirectorComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieDirectorComparatorLowerCaseJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieTitleComparatorEqual;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieTitleComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieTitleComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerBirthdateComparator10Years;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerBirthdateComparator2Years;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerHeightComparatorEqual;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerHeightComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerHeightComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerNameComparatorEqual;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerNameComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerNameComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerPositionsComparatorEqual;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerPositionsComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerPositionsComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerTeamsComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerWeightComparatorEqual;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerWeightComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerWeightComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.RuleLearner;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.matching.rules.WekaMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class IR_using_machine_learning {
	
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
		
		// load the training set
		System.out.println("*\n*\tLoading gold standard for Player Stat to DBpedia \n*");
		LinearCombinationMatchingRule<Player, Attribute> matchingRulePlayerStatDBpedia = new LinearCombinationMatchingRule<>(0.8);
		MatchingGoldStandard gsTestPlayerStatDBpedia = new MatchingGoldStandard();
		matchingRulePlayerStatDBpedia.activateDebugReport("data/output/debugResultsMatchingRulePlayerStatDBpedia.csv", 1000, gsTestPlayerStatDBpedia);
		gsTestPlayerStatDBpedia.loadFromCSVFile(new File("data/goldstandard/gs_player_stat_2_dbpedia.csv"));

		// create a matching rule
		String options[] = new String[] { "-S" };
		String modelType = "SimpleLogistic"; // use a logistic regression
		WekaMatchingRule<Movie, Attribute> matchingRule = new WekaMatchingRule<>(0.7, modelType, options);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 1000, gsTestPlayerStatDBpedia);
		
		// add comparators
		matchingRulePlayerStatDBpedia.addComparator(new PlayerNameComparatorEqual(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerNameComparatorJaccard(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerNameComparatorLevenshtein(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerBirthdateComparator2Years(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerBirthdateComparator10Years(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerHeightComparatorEqual(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerHeightComparatorJaccard(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerHeightComparatorLevenshtein(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerPositionsComparatorEqual(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerPositionsComparatorJaccard(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerPositionsComparatorLevenshtein(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerTeamsComparatorJaccard(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerWeightComparatorEqual(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerWeightComparatorJaccard(), 1);
		matchingRulePlayerStatDBpedia.addComparator(new PlayerWeightComparatorLevenshtein(), 1);
		
		
		// train the matching rule's model
		logger.info("*\tLearning matching rule\t*");
		RuleLearner<Player, Attribute> learner = new RuleLearner<>();
		learner.learnMatchingRule(dataPlayerStat, dataPlayerDBpedia, null, matchingRulePlayerStatDBpedia, gsTestPlayerStatDBpedia);
//		logger.info(String.format("Matching rule is:\n%s", matchingRulePlayerStatDBpedia.getModelDescription()));
		
		// create a blocker (blocking strategy)
		StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingByKeyNameGenerator());
//    			SortedNeighbourhoodBlocker<Movie, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new MovieBlockingKeyByDecadeGenerator(), 1);
		blocker.collectBlockSizeData("data/output/debugResultsBlocking.csv", 100);
		
		// Initialize Matching Engine
		MatchingEngine<Player, Attribute> enginePlayerStatDBpedia = new MatchingEngine<>();
		// Execute the matching
		logger.info("*\tRunning identity resolution\t*");
		Processable<Correspondence<Player, Attribute>> correspondences = enginePlayerStatDBpedia.runIdentityResolution(
				dataPlayerStat, dataPlayerDBpedia, null, matchingRulePlayerStatDBpedia,
				blocker);

		// write the correspondences to the output file
//		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/data/output/correspondences/player_stat_2_dbpedia_correspondences.csv"), correspondences);

		// load the gold standard (test set)
		logger.info("*\tLoading gold standard\t*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File(
				"data/goldstandard/gs_player_stat_2_dbpedia.csv"));
		
		// evaluate your result
		logger.info("*\tEvaluating result\t*");
		MatchingEvaluator<Player, Attribute> evaluator = new MatchingEvaluator<Player, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences,
				gsTest);
		
		// print the evaluation result
		logger.info("Players - Stat <-> Players - DBpedia");
		logger.info(String.format(
				"Precision: %.4f",perfTest.getPrecision()));
		logger.info(String.format(
				"Recall: %.4f",	perfTest.getRecall()));
		logger.info(String.format(
				"F1: %.4f",perfTest.getF1()));
    }
}
