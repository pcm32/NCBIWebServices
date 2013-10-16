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

import java.util.List;

/**
 *
 * @author pmoreno
 */
public abstract class ESummaryResult {

    /**
     * Returns all the string keywords for which a single string should be stored in the ESummaryResult. This is for
     * parsing XML elements that only have one character field (and don't hold a list of elements). When ever one of this 
     * keywords is recognized, them the ESummaryXMLResponseParser should only parse the next CHARACTER event of the XML.
     * 
     * @return List of keywords that should be processed as single unit in the XML.
     */
    public abstract List<? extends Enum> getScalarKeywords();

    /**
     * Returns all the string keywords for which a list of strings should be stored in the ESummaryResult. This is for
     * parsing XML elements that hold a list of elements. When ever one of this 
     * keywords is recognized, them the ESummaryXMLResponseParser should parse the incoming list of the XML.
     * 
     * @return 
     */
    public abstract List<? extends Enum> getListKeywords();

    /**
     * With this method, a particular ESummaryResult implementation maps the results obtained by the parser for that
     * keyword with fields that the ESummaryResult impl. should fill.
     * 
     * @param keyword
     * @param parseList 
     */
    public abstract void addListForKeyword(Enum keyword, List<String> parseList);

    /**
     * With this method, a particular ESummaryResult implementation maps the results obtained by the parser for that
     * keyword with fields that the ESummaryResult impl. should fill.
     * 
     * @param keyword
     * @param parseList 
     */
    public abstract void addScalarForKeyword(Enum keyword, String followingCharacters);

    /**
     * Here any final wrapping can be done by the ESummaryResult before being stored. For instance, translating sourceNames
     * and Identifiers to CrossReference objects.
     */
    public void wrap() {
        
    }
    
}
