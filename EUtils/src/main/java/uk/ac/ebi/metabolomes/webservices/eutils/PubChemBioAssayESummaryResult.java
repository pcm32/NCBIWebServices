/*
 * Copyright (C) 2013 Pablo Moreno <pablacious at users.sf.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.ebi.metabolomes.webservices.eutils;

import org.apache.log4j.Logger;
import uk.ac.ebi.mdk.domain.DefaultIdentifierFactory;
import uk.ac.ebi.mdk.domain.identifier.IdentifierFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @name    PubChemCompoundESummaryResult
 * @date    2011.10.30
 * @version $Rev$ : Last Changed $Date$
 * @author  pmoreno
 * @author  $Author$ (this version)
 * @brief   Eutils ESummary request result for PubChem Compounds
 *
 */
public class PubChemBioAssayESummaryResult extends ESummaryResult {

    private static final Logger LOGGER = Logger.getLogger(PubChemBioAssayESummaryResult.class);
    private static final IdentifierFactory FACTORY = DefaultIdentifierFactory.getInstance();
    private List<String> sources;
    private String name;
    private String description;

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * This enum holds the fields that are lists (more than one value)
     * that are currently parsed by the XML parser. If more list fields are desired for parsing (that are present in the
     * response, they should be added here).
     */
    public enum PubChemBioAssayESummaryListFields {

        SourceNameList;
    }

    /**
     * This enum holds the fields that are scalars (just one value) that are currently parsed by the XML parser. If more
     * scalar fields are desired for parsing, they should be added here.
     */
    public enum PubChemBioAssayESummarySingleFields {

        Id, AssayName, AssayDescription;
    }
    /**
     * This is all internal representation.
     */
    private String id;

    public List<PubChemBioAssayESummarySingleFields> getScalarKeywords() {
        return Arrays.asList(PubChemBioAssayESummarySingleFields.values());
    }

    public List<PubChemBioAssayESummaryListFields> getListKeywords() {
        return Arrays.asList(PubChemBioAssayESummaryListFields.values());
    }

    public void addListForKeyword(Enum keyword, List<String> parseList) {
        if (keyword instanceof PubChemBioAssayESummaryListFields) {
            switch ((PubChemBioAssayESummaryListFields) keyword) {
                case SourceNameList:
                    this.setSources(parseList);
                    break;
            }
        }
    }

    public void addScalarForKeyword(Enum keyword, String followingCharacters) {
        if (keyword instanceof PubChemBioAssayESummarySingleFields) {
            switch ((PubChemBioAssayESummarySingleFields) keyword) {
                case Id:
                    this.setId(followingCharacters);
                    break;
                case AssayName:
                    this.setName(followingCharacters);
                    break;
                case AssayDescription:
                    this.setDescription(followingCharacters);
                    break;
            }
        }
    }

    @Override
    public void wrap() {
        super.wrap();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}
