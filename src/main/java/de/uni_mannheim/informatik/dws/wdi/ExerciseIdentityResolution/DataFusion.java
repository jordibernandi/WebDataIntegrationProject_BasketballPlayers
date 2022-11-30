package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;
import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroupFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.*;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.*;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.*;
import org.slf4j.Logger;

public class DataFusion {

    private static final Logger logger = WinterLogManager.activateLogger("trace");

    public static void main( String[] args ) throws Exception
    {
        // Load the Data into FusibleDataSet
    	System.out.println("*\n*\tLoading datasets\n*");
        FusibleDataSet<Player, Attribute> dsPlayerStat = new FusibleHashedDataSet<>();
        new PlayerXMLReader().loadFromXML(new File("data/input/player_stat.xml"), "/players/player", dsPlayerStat);
        dsPlayerStat.printDataSetDensityReport();

        FusibleDataSet<Player, Attribute> dsPlayerDBpedia = new FusibleHashedDataSet<>();
        new PlayerXMLReader().loadFromXML(new File("data/input/player_dbpedia.xml"), "/players/player", dsPlayerDBpedia);
        dsPlayerDBpedia.printDataSetDensityReport();

        FusibleDataSet<Player, Attribute> dsPlayerSalary = new FusibleHashedDataSet<>();
        new PlayerXMLReader().loadFromXML(new File("data/input/player_salary.xml"), "/players/player", dsPlayerSalary);
        dsPlayerSalary.printDataSetDensityReport();

        FusibleDataSet<Player, Attribute> dsPlayerInjury = new FusibleHashedDataSet<>();
        new PlayerXMLReader().loadFromXML(new File("data/input/player_injury.xml"), "/players/player", dsPlayerInjury);
        dsPlayerInjury.printDataSetDensityReport();

        // Scores (e.g. from rating)
        dsPlayerStat.setScore(1.0);
        dsPlayerDBpedia.setScore(2.0);
        dsPlayerSalary.setScore(3.0);
        dsPlayerInjury.setScore(4.0);

        // Date (e.g. last update)
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter(Locale.ENGLISH);

        dsPlayerStat.setDate(LocalDateTime.parse("2019-03-15", formatter));
        dsPlayerDBpedia.setDate(LocalDateTime.parse("2019-03-15", formatter));
        dsPlayerSalary.setDate(LocalDateTime.parse("2018-12-31", formatter));
        dsPlayerInjury.setDate(LocalDateTime.parse("2018-12-21", formatter));

        // load correspondences
        System.out.println("*\n*\tLoading correspondences\n*");
        CorrespondenceSet<Player, Attribute> correspondences = new CorrespondenceSet<>();
        correspondences.loadCorrespondences(new File("data/output/correspondences/player_stat_2_dbpedia_correspondences.csv"),dsPlayerStat, dsPlayerDBpedia);
        correspondences.loadCorrespondences(new File("data/output/correspondences/player_stat_2_salary_correspondences.csv"),dsPlayerStat, dsPlayerSalary);
        correspondences.loadCorrespondences(new File("data/output/correspondences/player_stat_2_injury_correspondences.csv"),dsPlayerStat, dsPlayerInjury);

        correspondences.printGroupSizeDistribution();

        // load the gold standard
        System.out.println("*\n*\tEvaluating results\n*");
        DataSet<Player, Attribute> gs = new FusibleHashedDataSet<>();
        new PlayerXMLReader().loadFromXML(new File("data/goldstandard/gold.xml"), "/players/player", gs);

        for(Player p : gs.get()) {
            System.out.println(String.format("gs: %s", p.getIdentifier()));
        }

        // define the fusion strategy
        DataFusionStrategy<Player, Attribute> strategy = new DataFusionStrategy<>(new PlayerXMLReader());
        // write debug results to file
        strategy.activateDebugReport("data/output/debugResultsDatafusion.csv", 100000000, gs);

        //add attribute fusers and evaluation rules
//        strategy.addAttributeFuser(Player.NAME, new NameFuserVoting(),new NameEvaluationRule());
        strategy.addAttributeFuser(Player.NAME, new NameFuserLongestString(),new NameEvaluationRule());
        strategy.addAttributeFuser(Player.BIRTHDATE, new BirthDateFuserFavourSource(), new BirthDateEvaluationRule());
        strategy.addAttributeFuser(Player.BIRTHPLACE, new BirthPlaceFuserLongestString(),new BirthPlaceEvaluationRule());
        strategy.addAttributeFuser(Player.HEIGHT, new HeightFuserFavourSource(),new HeightEvaluationRule());
        strategy.addAttributeFuser(Player.WEIGHT, new WeightFuserFavourSource(),new WeightEvaluationRule());
        strategy.addAttributeFuser(Player.COLLEGE, new CollegeFuserLongestString(),new CollegeEvaluationRule());
        strategy.addAttributeFuser(Player.YEARSTART, new YearStartFuserFavourSource(),new YearStartEvaluationRule());
        strategy.addAttributeFuser(Player.YEAREND, new YearEndFuserFavourSource(),new YearEndEvaluationRule());
        strategy.addAttributeFuser(Player.POSITIONS,new PositionsFuserUnion(), new PositionsEvaluationRule());
        strategy.addAttributeFuser(Player.TEAMS,new TeamsFuserUnion(), new TeamsEvaluationRule());
        strategy.addAttributeFuser(Player.AWARDS,new AwardsFuserUnion(), new AwardsEvaluationRule());
        strategy.addAttributeFuser(Player.LEAGUES,new LeaguesFuserUnion(), new LeaguesEvaluationRule());
        strategy.addAttributeFuser(Player.SALARIES, new SalariesFuserUnion(), new SalariesEvaluationRule());
        strategy.addAttributeFuser(Player.INJURIES, new InjuriesFuserUnion(), new InjuriesEvaluationRule());

        // create the fusion engine
        try{
            DataFusionEngine<Player, Attribute> engine = new DataFusionEngine<>(strategy);

            // print consistency report
            engine.printClusterConsistencyReport(correspondences, null);

            // print record groups sorted by consistency
            engine.writeRecordGroupsByConsistency(new File("data/output/recordGroupConsistencies.csv"), correspondences, null);

            // run the fusion
            System.out.println("*\n*\tRunning data fusion\n*");
            FusibleDataSet<Player, Attribute> fusedDataSet = engine.run(correspondences, null);
            // write the result
            new PlayerXMLFormatter().writeXML(new File("data/output/fused.xml"), fusedDataSet);

            // evaluate
            DataFusionEvaluator<Player, Attribute> evaluator = new DataFusionEvaluator<>(strategy, new RecordGroupFactory<Player, Attribute>());

            double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

            System.out.println(String.format("Accuracy: %.2f", accuracy));

            FusibleDataSet<Player, Attribute> dsfusi = new FusibleHashedDataSet<>();
            new PlayerXMLReader().loadFromXML(new File("data/output/fused.xml"), "/players/player", dsfusi);
            dsfusi.printDataSetDensityReport();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }



    }
}