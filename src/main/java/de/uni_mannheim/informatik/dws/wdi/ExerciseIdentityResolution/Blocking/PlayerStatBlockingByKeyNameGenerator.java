package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.PlayerStat;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class PlayerStatBlockingByKeyNameGenerator extends RecordBlockingKeyGenerator<PlayerStat, Attribute> {

    private static final long serialVersionUID = 1L;

    @Override
    public void generateBlockingKeys(PlayerStat record, Processable<Correspondence<Attribute, Matchable>> processable, DataIterator<Pair<String, PlayerStat>> resultCollector) {
        String name  = record.getName();
        String blockingKeyValue = "";
        if (name.length() > 2) {
            blockingKeyValue = name.substring(0, 2);
        } else {
            blockingKeyValue = name;
        }

        resultCollector.next(new Pair<>(blockingKeyValue, record));
    }
}
