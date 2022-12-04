package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Injury;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Salary;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.Voting;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.MostRecent;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

import java.util.List;

public class InjuriesMostRecent extends AttributeValueFuser<List<Injury>, Player, Attribute> {

    public InjuriesMostRecent() {
        super(new MostRecent<>());
    }

    @Override
    public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Player.INJURIES);
    }

    @Override
    public List<Injury> getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getInjuries();
    }

    @Override
    public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<List<Injury>, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setInjuries(fused.getValue());
        fusedRecord.setAttributeProvenance(Player.INJURIES, fused.getOriginalIds());
    }

}