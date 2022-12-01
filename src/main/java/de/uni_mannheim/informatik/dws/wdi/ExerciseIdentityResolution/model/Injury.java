/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Injury extends AbstractRecord<Attribute> implements Serializable {

    private static final long serialVersionUID = 1L;
    private LocalDate injuryDate;
    private String injuryNote;

    public Injury(String identifier, String provenance) {
        super(identifier, provenance);
    }

    public LocalDate getInjuryDate() {
        return injuryDate;
    }
    public void setInjuryDate(LocalDate injuryDate) {
        this.injuryDate = injuryDate;
    }

    public String getInjuryNote() {
        return injuryNote;
    }
    public void setInjuryNote(String injuryNote) {
        this.injuryNote = injuryNote;
    }

//    @Override
//    public int hashCode() {
//        int result = 31 + ((name == null) ? 0 : name.hashCode());
//        return result;
//    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        Actor other = (Actor) obj;
//        if (name == null) {
//            if (other.name != null)
//                return false;
//        } else if (!name.equals(other.name))
//            return false;
//        return true;
//    }

    public static final Attribute INJURYDATE = new Attribute("InjuryDate");
    public static final Attribute INJURYNOTE = new Attribute("InjuryNote");

    /* (non-Javadoc)
     * @see de.uni_mannheim.informatik.wdi.model.Record#hasValue(java.lang.Object)
     */
    @Override
    public boolean hasValue(Attribute attribute) {
        if(attribute==INJURYDATE)
            return injuryDate!=null;
        else if(attribute==INJURYNOTE)
            return injuryNote!=null;
        return false;
    }

    @Override
    public String toString() {
        return String.format("[Injury: %s]", getInjuryDate().toString());
    }
}
