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

/**
 * @name    PubChemSubstanceXMLResponseParser
 * @date    2011.10.30
 * @version $Rev$ : Last Changed $Date$
 * @author  pmoreno
 * @author  $Author$ (this version)
 * @brief   ...class description...
 *
 */
public class PubChemCompoundXMLResponseParser extends ESummaryXMLResponseParser<PubChemCompoundESummaryResult> {
    
    private static final Logger LOGGER = Logger.getLogger(PubChemCompoundXMLResponseParser.class);

    @Override
    PubChemCompoundESummaryResult getNewESummaryResult() {
        return new PubChemCompoundESummaryResult();
    }
}
