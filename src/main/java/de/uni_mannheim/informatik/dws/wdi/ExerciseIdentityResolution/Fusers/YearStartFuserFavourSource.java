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

public class YearStartFuserFavourSource extends AttributeValueFuser<Integer, Player, Attribute> {

    public YearStartFuserFavourSource() {
        super(new FavourSources<Integer, Player, Attribute>());
    }

    @Override
    public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Player.YEARSTART);
    }

    @Override
    public Integer getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getYearStart();
    }

    @Override
    public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<Integer, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setYearStart(fused.getValue());
        fusedRecord.setAttributeProvenance(Player.YEARSTART, fused.getOriginalIds());
    }

}