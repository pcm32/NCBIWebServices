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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @name    ELinkXMLResponseParser
 * @date    2011.10.30
 * @version $Rev$ : Last Changed $Date$
 * @author  pmoreno
 * @author  $Author$ (this version)
 * @brief   ...class description...
 *
 */
public abstract class ESummaryXMLResponseParser<T extends ESummaryResult> extends XMLResponseParser {

    private static final Logger LOGGER = Logger.getLogger(ESummaryXMLResponseParser.class);

    /**
     * Parses the whole ESummaryResult XML object, delivering a List of ESummaryResults.
     * 
     * @param in the input stream through which the response the response can be read.
     * @return multimap with the mappings from the XML.
     * @throws javax.xml.stream.XMLStreamException
     */
    public List<T> parseESummaryResult(InputStream in) throws XMLStreamException {

        XMLInputFactory2 xmlif = (XMLInputFactory2) XMLInputFactory2.newInstance();
        xmlif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.FALSE);
        xmlif.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        xmlif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.TRUE);
        xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
        xmlif.configureForSpeed();

        XMLStreamReader2 xmlr = (XMLStreamReader2) xmlif.createXMLStreamReader(in);

        int event;

        List<T> results = new ArrayList<T>();
        T currentResult = getNewESummaryResult();

        while (xmlr.hasNext()) {
            event = xmlr.next();

            switch1:
            switch (event) {
                case XMLEvent.START_DOCUMENT:
                    break;
                case XMLEvent.START_ELEMENT:
                    //LOGGER.info("Start Element: "+xmlr.getLocalName());
                    //LOGGER.info("Attributes: "+getAttributes(xmlr));
                    if (xmlr.getLocalName().equalsIgnoreCase("Item")) {
                        boolean done = false;
                        
                        for (Enum keyword : currentResult.getScalarKeywords()) {
                            if(hasAttributeNameWithValue(xmlr, keyword.toString())) {
                                //LOGGER.info("Entering addScalarForKeyword: "+keyword.toString()+" for "+xmlr.getLocalName());
                                currentResult.addScalarForKeyword(keyword,getFollowingCharacters(xmlr));
                                break switch1;
                            }
                        }
                        for (Enum keyword : currentResult.getListKeywords()) {
                            if (hasAttributeNameWithValue(xmlr, keyword.toString())) {
                                //LOGGER.info("Entering addListForKeyword: "+keyword.toString()+" for "+xmlr.getLocalName());
                                currentResult.addListForKeyword(keyword, parseList(xmlr));
                                break switch1;
                            }
                        }
                    }
                    if(xmlr.getLocalName().equalsIgnoreCase("Id")) {
                        for (Enum keyword : currentResult.getScalarKeywords()) {
                            if(keyword.toString().equalsIgnoreCase("Id")) {
                                currentResult.addScalarForKeyword(keyword, getFollowingCharacters(xmlr));
                                break switch1;
                            }
                        }
                    }
                    /*
                    if (xmlr.getLocalName().equalsIgnoreCase("Item") && hasAttributeNameWithValue(xmlr, "SID")) {
                        currentResult.setId(getFollowingCharacters(xmlr));
                    } else if (xmlr.getLocalName().equalsIgnoreCase("Item") && hasAttributeNameWithValue(xmlr, "SourceNameList")) {
                        currentResult.setSourceNames(parseList(xmlr));
                    } else if (xmlr.getLocalName().equalsIgnoreCase("Item") && hasAttributeNameWithValue(xmlr, "SourceID")) {
                        currentResult.addSourceID(getFollowingCharacters(xmlr));
                    } else if (xmlr.getLocalName().equalsIgnoreCase("Item") && hasAttributeNameWithValue(xmlr, "DBUrl")) {
                        currentResult.setDBUrl(getFollowingCharacters(xmlr));
                    } else if (xmlr.getLocalName().equalsIgnoreCase("Item") && hasAttributeNameWithValue(xmlr, "SynonymList")) {
                        currentResult.setSynonyms(parseList(xmlr));
                    }*/

                    break;
                case XMLEvent.END_ELEMENT:
                    //LOGGER.info("End Element: "+xmlr.getLocalName());
                    if (xmlr.getLocalName().equalsIgnoreCase("DocSum")) {
                        currentResult.wrap();
                        results.add(currentResult);
                        currentResult = getNewESummaryResult();
                    }
                    break;
            }
        }
        xmlr.closeCompletely();
        return results;
    }


    abstract T getNewESummaryResult();

    String getAttributes(XMLStreamReader2 xmlr) {
        List<String> attributes = new ArrayList<String>();
        for(int i=0;i<xmlr.getAttributeCount();i++) {
            attributes.add(xmlr.getAttributeLocalName(i)+":"+xmlr.getAttributeValue(i));
        }
        return StringUtils.join(attributes, ", ");
    }

}
