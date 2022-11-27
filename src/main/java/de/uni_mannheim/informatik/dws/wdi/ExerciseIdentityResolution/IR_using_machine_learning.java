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
		MatchingGoldStandard gsTrainingPlayerStatDBpedia = new MatchingGoldStandard();
		MatchingGoldStandard gsTrainingPlayerStatSalary = new MatchingGoldStandard();
        MatchingGoldStandard gsTrainingPlayerStatInjury = new MatchingGoldStandard();

        gsTrainingPlayerStatDBpedia.loadFromCSVFile(new File("data/goldstandard/gs_player_stat_2_dbpedia_train.csv"));
        gsTrainingPlayerStatSalary.loadFromCSVFile(new File("data/goldstandard/gs_player_stat_2_salary_train.csv"));
        gsTrainingPlayerStatInjury.loadFromCSVFile(new File("data/goldstandard/gs_player_stat_2_injury_train.csv"));

        // create matching rules
        String options[] = new String[] { "-S" };
        String modelType = "SimpleLogistic"; // use a logistic regression
        WekaMatchingRule<Player, Attribute> matchingRulePlayerStatDBpedia = new WekaMatchingRule<>(0.8, modelType, options);
		matchingRulePlayerStatDBpedia.activateDebugReport("data/output/debugResultsMatchingRuleStatDBpedia.csv", 1000, gsTrainingPlayerStatDBpedia);

        WekaMatchingRule<Player, Attribute> matchingRulePlayerStatSalary = new WekaMatchingRule<>(0.7, modelType, options);
		matchingRulePlayerStatSalary.activateDebugReport("data/output/debugResultsMatchingRuleStatSalary.csv", 1000, gsTrainingPlayerStatSalary);

        WekaMatchingRule<Player, Attribute> matchingRulePlayerStatInjury = new WekaMatchingRule<>(0.75, modelType, options);
        matchingRulePlayerStatInjury.activateDebugReport("data/output/debugResultsMatchingRuleStatInjury.csv", 1000, gsTrainingPlayerStatInjury);

		//added comparators for StatDBpedia
		matchingRulePlayerStatDBpedia.addComparator(new PlayerNameComparatorJaccard());
		matchingRulePlayerStatDBpedia.addComparator(new PlayerBirthDateComparatorEqual());
		matchingRulePlayerStatDBpedia.addComparator(new PlayerHeightComparatorPercentageSim());
		matchingRulePlayerStatDBpedia.addComparator(new PlayerWeightComparatorPercentageSim());

		//added comparators for StatSalary
		matchingRulePlayerStatSalary.addComparator(new PlayerNameComparatorJaccard());
		matchingRulePlayerStatSalary.addComparator(new PlayerCareerComparatorRange());

		//added comparators for StatInjury
		matchingRulePlayerStatInjury.addComparator(new PlayerNameComparatorJaccard());
		matchingRulePlayerStatInjury.addComparator(new PlayerPositionsComparatorOverlap());

		// train the matching rule's model
        System.out.println("*\n*\tLearning matching rule\n*");
        RuleLearner<Player, Attribute> learnerPlayerStatDBpedia = new RuleLearner<>();
        RuleLearner<Player, Attribute> learnerPlayerStatSalary = new RuleLearner<>();
        RuleLearner<Player, Attribute> learnerPlayerStatInjury = new RuleLearner<>();

        learnerPlayerStatDBpedia.learnMatchingRule(dataPlayerStat, dataPlayerDBpedia, null, matchingRulePlayerStatDBpedia, gsTrainingPlayerStatDBpedia);
        learnerPlayerStatSalary.learnMatchingRule(dataPlayerStat, dataPlayerSalary, null, matchingRulePlayerStatSalary, gsTrainingPlayerStatSalary);
        learnerPlayerStatInjury.learnMatchingRule(dataPlayerStat, dataPlayerInjury, null, matchingRulePlayerStatInjury, gsTrainingPlayerStatInjury);
        System.out.println(String.format("Matching rule for Player Stat <-> DBpedia  is:\n%s", matchingRulePlayerStatDBpedia.getModelDescription()));
        System.out.println(String.format("Matching rule for Player Stat <-> Salary is:\n%s", matchingRulePlayerStatSalary.getModelDescription()));
        System.out.println(String.format("Matching rule for Player Stat <-> Injury is:\n%s", matchingRulePlayerStatInjury.getModelDescription()));

        // create a blocker (blocking strategy)
		StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingByKeyNameGenerator());
        blocker.collectBlockSizeData("data/output/debugResultsBlockingML.csv", 100);

        // Initialize Matching Engine
        MatchingEngine<Player, Attribute> enginePlayerStatDBpedia = new MatchingEngine<>();
 		MatchingEngine<Player, Attribute> enginePlayerStatSalary = new MatchingEngine<>();
 		MatchingEngine<Player, Attribute> enginePlayerStatInjury = new MatchingEngine<>();

        // Execute the matchings
 		Processable<Correspondence<Player, Attribute>> correspondencesPlayerStatDBpedia = enginePlayerStatDBpedia.runIdentityResolution(
 				dataPlayerStat, dataPlayerDBpedia, null, matchingRulePlayerStatDBpedia, blocker);
 		Processable<Correspondence<Player, Attribute>> correspondencesPlayerStatSalary = enginePlayerStatSalary.runIdentityResolution(
 				dataPlayerStat, dataPlayerSalary, null, matchingRulePlayerStatSalary, blocker);
 		Processable<Correspondence<Player, Attribute>> correspondencesPlayerStatInjury = enginePlayerStatInjury.runIdentityResolution(
 				dataPlayerStat, dataPlayerInjury , null, matchingRulePlayerStatInjury, blocker);

 		// write the correspondences to the output file
        new CSVCorrespondenceFormatter().writeCSV(new File("data/output/correspondences/player_stat_2_dbpedia_correspondences.csv"), correspondencesPlayerStatDBpedia);
        new CSVCorrespondenceFormatter().writeCSV(new File("data/output/correspondences/player_stat_2_salary_correspondences.csv"), correspondencesPlayerStatSalary);
        new CSVCorrespondenceFormatter().writeCSV(new File("data/output/correspondences/player_stat_2_injury_correspondences.csv"), correspondencesPlayerStatInjury);

        // load the gold standard (test set)
        System.out.println("*\n*\tLoading gold standard\n*");
        MatchingGoldStandard gsTestPlayerStatDBpedia = new MatchingGoldStandard();
        MatchingGoldStandard gsTestPlayerStatSalary = new MatchingGoldStandard();
        MatchingGoldStandard gsTestPlayerStatInjury = new MatchingGoldStandard();

        gsTestPlayerStatDBpedia.loadFromCSVFile(new File("data/goldstandard/gs_player_stat_2_dbpedia_test.csv"));
        gsTestPlayerStatSalary.loadFromCSVFile(new File("data/goldstandard/gs_player_stat_2_salary_test.csv"));
        gsTestPlayerStatInjury.loadFromCSVFile(new File("data/goldstandard/gs_player_stat_2_injury_test.csv"));
        System.out.println(matchingRulePlayerStatDBpedia.getModelDescription());
        System.out.println(matchingRulePlayerStatSalary.getModelDescription());
        System.out.println(matchingRulePlayerStatInjury.getModelDescription());

        // evaluate your result
        System.out.println("*\n*\tEvaluating result\n*");
        MatchingEvaluator<Player, Attribute> evaluatorPlayerStatDBpedia = new MatchingEvaluator<Player, Attribute>();
        MatchingEvaluator<Player, Attribute> evaluatorPlayerStatSalary = new MatchingEvaluator<Player, Attribute>();
        MatchingEvaluator<Player, Attribute> evaluatorPlayerStatInjury = new MatchingEvaluator<Player, Attribute>();
        Performance perfTestPlayerStatDBpedia = evaluatorPlayerStatDBpedia.evaluateMatching(correspondencesPlayerStatDBpedia,
                gsTestPlayerStatDBpedia);
        Performance perfTestPlayerStatSalary = evaluatorPlayerStatSalary.evaluateMatching(correspondencesPlayerStatSalary,
                gsTestPlayerStatSalary);
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
