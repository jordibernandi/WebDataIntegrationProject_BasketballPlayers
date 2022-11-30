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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class SalaryXMLFormatter extends XMLFormatter<Salary> {

    @Override
    public Element createRootElement(Document doc) {
        return doc.createElement("salaries");
    }

    @Override
    public Element createElementFromRecord(Salary record, Document doc) {
        Element salary = doc.createElement("salary");

        if(!Double.toString(record.getAmount()).isEmpty()) {
            salary.appendChild(createTextElement("amount", Double.toString(record.getAmount()), doc));
        }
        if(!Integer.toString(record.getSeasonStartYear()).isEmpty()) {
            salary.appendChild(createTextElement("seasonStartYear", Integer.toString(record.getSeasonStartYear()), doc));
        }
        if(!Integer.toString(record.getSeasonEndYear()).isEmpty()) {
            salary.appendChild(createTextElement("seasonEndYear", Integer.toString(record.getSeasonEndYear()), doc));
        }

        return salary;
    }
}
