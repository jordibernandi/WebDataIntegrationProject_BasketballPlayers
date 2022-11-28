package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.LongestString;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;

public class CollegeFuserLongestString extends
        AttributeValueFuser<String, Player, Attribute> {

    public CollegeFuserLongestString() {
        super(new LongestString<Player, Attribute>());
    }

    @Override
    public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Player.COLLEGE);
    }

    @Override
    public String getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getCollege();
    }

    @Override
    public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<String, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setCollege(fused.getValue());
        fusedRecord.setAttributeProvenance(Player.COLLEGE,
                fused.getOriginalIds());
    }

}