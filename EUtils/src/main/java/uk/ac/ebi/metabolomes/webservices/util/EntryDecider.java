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

import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author pmoreno
 */
public class EntryDecider {

    private Pattern prefixPattern;

    /**
     * 
     * @param query
     * @param candidates
     * @return
     * @deprecated {@see getOrderedCandidates(String, Map<String , String> candidates)} This uses a list and thus does not colapse entries with duplicate
     */
    @Deprecated
    public Set<CandidateEntry> decideBestCandidate( String query , Map<String , String> candidates ) {

        Set<CandidateEntry> orderedCand = new TreeSet<CandidateEntry>();
        for ( String identifier : candidates.keySet() ) {
            CandidateEntry candidateT = new CandidateEntry();
            candidateT.setId( identifier );
            candidateT.setDesc( candidates.get( identifier ) );
            String prefix = this.identiferPrefix( identifier );
            if ( prefix != null && candidateT.getDesc().contains( prefix ) ) {
                candidateT.setDesc( candidateT.getDesc().replace( prefix , "" ) );
            }
            candidateT.setDistance( StringUtils.getLevenshteinDistance( query.toLowerCase().trim() ,
                                                                        candidateT.getDesc().toLowerCase().trim() ) );

            orderedCand.add( candidateT );
        }

        return orderedCand;
    }
    
    public List<CandidateEntry> getOrderedCandidates( String query , Map<String , String> candidates ) {

        List<CandidateEntry> orderedCand = new ArrayList<CandidateEntry>();
        for ( String identifier : candidates.keySet() ) {
            CandidateEntry candidateT = new CandidateEntry();
            candidateT.setId( identifier );
            candidateT.setDesc( candidates.get( identifier ) );
            String prefix = this.identiferPrefix( identifier );
            if ( prefix != null && candidateT.getDesc().contains( prefix ) ) {
                candidateT.setDesc( candidateT.getDesc().replace( prefix , "" ) );
            }
            candidateT.setDistance( StringUtils.getLevenshteinDistance( query.toLowerCase().trim() ,
                                                                        candidateT.getDesc().toLowerCase().trim() ) );

            orderedCand.add( candidateT );
        }
        
        Collections.sort( orderedCand );

        return orderedCand;
    }

    /**
     *
     * @param query
     * @param candidates
     * @return
     * @deprecated {@see getOrderedCandidates(String, Collection<String>)} This uses a list and thus does not colapse entries with duplicate
     */
    @Deprecated
    public Set<CandidateEntry> decideBestCandidate( String query , Collection<String> candidates ) {
        Set<CandidateEntry> orderedCand = new TreeSet<CandidateEntry>();
        for ( String candidate : candidates ) {
            CandidateEntry candidateT = new CandidateEntry();
            //candidateT.setId(candidate);
            candidateT.setDesc( candidate );
            String prefix = this.identiferPrefix( candidate );
            if ( prefix != null && candidateT.getDesc().contains( prefix ) ) {
                candidateT.setDesc( candidateT.getDesc().replace( prefix , "" ) );
            }
            candidateT.setDistance( StringUtils.getLevenshteinDistance( query.toLowerCase().trim() ,
                                                                        candidateT.getDesc().toLowerCase().trim() ) );

            orderedCand.add( candidateT );
        }

        return orderedCand;
    }

    public List<CandidateEntry> getOrderedCandidates( String query , Collection<String> candidates ) {

        String formatedQuery = query.toLowerCase( Locale.ENGLISH ).trim();

        List<CandidateEntry> orderedCand = new ArrayList<CandidateEntry>();

        for ( String candidate : candidates ) {

            String description = removePrefix( candidate );
            Integer distance = StringUtils.getLevenshteinDistance( formatedQuery ,
                                                                   description.toLowerCase( Locale.ENGLISH ).trim() );
            orderedCand.add( new CandidateEntry( "" ,
                                                 description ,
                                                 distance ,
                                                 "" ) );
        }

        Collections.sort( orderedCand );

        return orderedCand;
    }

    private String removePrefix( String description ) {
        String prefix = this.identiferPrefix( description );
        if ( prefix != null && description.contains( prefix ) ) {
            return description.replace( prefix , "" );
        }
        return description;
    }

    public EntryDecider() {
        this.prefixPattern = Pattern.compile( "([A-Z]+:)\\d" );
    }

    private String identiferPrefix( String identifer ) {
        Matcher prefixMatcher = this.prefixPattern.matcher( identifer );
        if ( prefixMatcher.find() ) {
            return prefixMatcher.group( 1 );
        } else {
            return null;
        }
    }
}
