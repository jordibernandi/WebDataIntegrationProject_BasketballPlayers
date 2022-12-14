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

public class WeightFuserFavourSource extends AttributeValueFuser<Float, Player, Attribute> {

    public WeightFuserFavourSource() {
        super(new FavourSources<Float, Player, Attribute>());
    }

    @Override
    public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Player.WEIGHT);
    }

    @Override
    public Float getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getWeight();
    }

    @Override
    public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<Float, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        if(fused.getValue() == null) {
            fusedRecord.setWeight(0);
        } else {
            fusedRecord.setWeight(fused.getValue());
        }
        fusedRecord.setAttributeProvenance(Player.WEIGHT, fused.getOriginalIds());
    }

}