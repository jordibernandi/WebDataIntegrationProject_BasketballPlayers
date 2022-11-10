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
import java.time.LocalDateTime;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Salary extends AbstractRecord<Attribute> implements Serializable {

    private static final long serialVersionUID = 1L;
    private double amount;
    private int seasonStartYear;
    private int seasonEndYear;

    public Salary(String identifier, String provenance) {
        super(identifier, provenance);
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getSeasonStartYear() {
        return seasonStartYear;
    }
    public void setSeasonStartYear(int seasonStartYear) {
        this.seasonStartYear = seasonStartYear;
    }

    public int getSeasonEndYear() { return seasonEndYear; }
    public void setSessionEndYear(int seasonEndYear) {
        this.seasonEndYear = seasonEndYear;
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
//        Player other = (Player) obj;
//        if (name == null) {
//            if (other.name != null)
//                return false;
//        } else if (!name.equals(other.name))
//            return false;
//        return true;
//    }

    public static final Attribute AMOUNT = new Attribute("Amount");
    public static final Attribute SEASONSTARTYEAR = new Attribute("SeasonStartYear");
    public static final Attribute SEASONENDYEAR = new Attribute("SeasonEndYear");

    @Override
    public boolean hasValue(Attribute attribute) {
        if(attribute==AMOUNT)
            return amount!=0;
        else if(attribute==SEASONSTARTYEAR)
            return seasonStartYear!=0;
        else if(attribute==SEASONENDYEAR)
            return seasonEndYear!=0;
        return false;
    }
}
