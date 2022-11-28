package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;

public class YearEndFuserFavourSource extends AttributeValueFuser<Integer, Player, Attribute> {

    public YearEndFuserFavourSource() {
        super(new FavourSources<Integer, Player, Attribute>());
    }

    @Override
    public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Player.YEAREND);
    }

    @Override
    public Integer getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getYearEnd();
    }

    @Override
    public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<Integer, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        if(fused.getValue() == null) {
            fusedRecord.setYearEnd(0);
        } else {
            fusedRecord.setYearEnd(fused.getValue());
        }
        fusedRecord.setAttributeProvenance(Player.YEAREND, fused.getOriginalIds());
    }

}