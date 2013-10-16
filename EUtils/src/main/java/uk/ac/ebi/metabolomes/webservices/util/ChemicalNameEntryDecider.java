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
package uk.ac.ebi.metabolomes.webservices.util;

import org.apache.log4j.Logger;
import uk.ac.ebi.mdk.tool.resolve.PseudoFingerprintChemicalNames;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @name    ChemicalNameEntryDecider
 * @date    2011.08.03
 * @version $Rev$ : Last Changed $Date$
 * @author  pmoreno
 * @author  $Author$ (this version)
 * @brief   Decorator of EntryDecider targeted for chemical names
 *
 */
public class ChemicalNameEntryDecider {

    private static final Logger LOGGER = Logger.getLogger( ChemicalNameEntryDecider.class );
    private EntryDecider decider;
    private PseudoFingerprintChemicalNames fingerprintChemicalNames;

    public ChemicalNameEntryDecider() {
        this.decider = new EntryDecider();
        this.fingerprintChemicalNames = new PseudoFingerprintChemicalNames();
    }

    /**
     * 
     * @param query
     * @param candidates
     * @return
     * @deprecated use getOrderedCandidates instead
     */
    @Deprecated
    public Set<CandidateEntry> decideBestCandidate( String query , Collection<String> candidates ) {

        Set<CandidateEntry> bestCands = this.decider.decideBestCandidate( query , candidates );
        for ( CandidateEntry candidateEntry : bestCands ) {
            if ( candidateEntry.getDistance() > 0 ) {
                // we re run the comparison with the pseudo fingerprinter
                int distance = fingerprintChemicalNames.lenvenshteinComparisonKeyed( query , candidateEntry.getDesc() );
                if ( distance < candidateEntry.getDistance() ) {
                    candidateEntry.setDistance( distance );
                    candidateEntry.setComment( "Compared through " + fingerprintChemicalNames.getClass().getSimpleName() );
                }
            } else // we only use the finger printer if there is no direct exact match.
            {
                continue;
            }
        }
        return bestCands;
    }

    public List<CandidateEntry> getOrderedCandidates( String query , Collection<String> candidates ) {

        List<CandidateEntry> bestCands = this.decider.getOrderedCandidates( query , candidates );
        for ( CandidateEntry candidateEntry : bestCands ) {
            if ( candidateEntry.getDistance() > 0 ) {
                // we re run the comparison with the pseudo fingerprinter
                int distance = fingerprintChemicalNames.lenvenshteinComparisonKeyed( query , candidateEntry.getDesc() );
                if ( distance < candidateEntry.getDistance() ) {
                    candidateEntry.setDistance( distance );
                    candidateEntry.setComment( "Compared through " + fingerprintChemicalNames.getClass().getSimpleName() );
                }
            } else // we only use the finger printer if there is no direct exact match.
            {
                continue;
            }
        }

        Collections.sort( bestCands );

        return bestCands;
    }
}
