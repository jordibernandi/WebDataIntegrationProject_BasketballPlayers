package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

import java.util.List;

public class LeaguesFuserFavourSource extends AttributeValueFuser<List<String>, Player, Attribute> {

    public LeaguesFuserFavourSource() {
        super(new FavourSources<List<String>, Player, Attribute>());
    }

    @Override
    public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Player.LEAGUES);
    }

    @Override
    public List<String> getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getLeagues();
    }

    @Override
    public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<List<String>, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setLeagues(fused.getValue());
        fusedRecord.setAttributeProvenance(Player.LEAGUES, fused.getOriginalIds());
    }

}