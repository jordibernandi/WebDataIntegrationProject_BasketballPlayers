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

public class AwardsFuserFavourSource extends AttributeValueFuser<List<String>, Player, Attribute> {

    public AwardsFuserFavourSource() {
        super(new FavourSources<List<String>, Player, Attribute>());
    }

    @Override
    public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Player.AWARDS);
    }

    @Override
    public List<String> getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getAwards();
    }

    @Override
    public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<List<String>, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setAwards(fused.getValue());
        fusedRecord.setAttributeProvenance(Player.AWARDS, fused.getOriginalIds());
    }

}