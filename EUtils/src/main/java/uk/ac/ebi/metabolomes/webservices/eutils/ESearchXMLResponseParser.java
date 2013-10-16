package uk.ac.ebi.metabolomes.webservices.eutils;

import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: pcm32
 * Date: 19/09/13
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class ESearchXMLResponseParser extends XMLResponseParser{

    /**
     * Parses the whole ESearchResult XML object, delivering a List of ESummaryResults.
     *
     * @param in the input stream through which the response the response can be read.
     * @return multimap with the mappings from the XML.
     * @throws javax.xml.stream.XMLStreamException
     */
    public ESearchResult parseESearchResult(InputStream in) throws XMLStreamException {

        XMLInputFactory2 xmlif = (XMLInputFactory2) XMLInputFactory2.newInstance();
        xmlif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.FALSE);
        xmlif.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        xmlif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.TRUE);
        xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
        xmlif.configureForSpeed();

        XMLStreamReader2 xmlr = (XMLStreamReader2) xmlif.createXMLStreamReader(in);

        int event;

        ESearchResult currentResult = getNewESearchResult();

        while (xmlr.hasNext()) {
            event = xmlr.next();

            switch1:
            switch (event) {
                case XMLEvent.START_DOCUMENT:
                    break;
                case XMLEvent.START_ELEMENT:
                    //LOGGER.info("Start Element: "+xmlr.getLocalName());
                    //LOGGER.info("Attributes: "+getAttributes(xmlr));
                    if (xmlr.getLocalName().equalsIgnoreCase("Count")) {
                        currentResult.setCount(getFollowingCharacters(xmlr));
//                        boolean done = false;

//                        for (Enum keyword : currentResult.getScalarKeywords()) {
//                            if(hasAttributeNameWithValue(xmlr, keyword.toString())) {
//                                //LOGGER.info("Entering addScalarForKeyword: "+keyword.toString()+" for "+xmlr.getLocalName());
//                                currentResult.addScalarForKeyword(keyword,getFollowingCharacters(xmlr));
//                                break switch1;
//                            }
//                        }
//                        for (Enum keyword : currentResult.getListKeywords()) {
//                            if (hasAttributeNameWithValue(xmlr, keyword.toString())) {
//                                //LOGGER.info("Entering addListForKeyword: "+keyword.toString()+" for "+xmlr.getLocalName());
//                                currentResult.addListForKeyword(keyword, parseList(xmlr));
//                                break switch1;
//                            }
//                        }
                    }
                    else if(xmlr.getLocalName().equalsIgnoreCase("RetMax")) {
                        currentResult.setRetMax(getFollowingCharacters(xmlr));
                    }
                    else if(xmlr.getLocalName().equalsIgnoreCase("RetStart")) {
                        currentResult.setRetStart(getFollowingCharacters(xmlr));
                    }
                    else if(xmlr.getLocalName().equalsIgnoreCase("Id")) {
                        currentResult.addId(getFollowingCharacters(xmlr));
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
//                    if (xmlr.getLocalName().equalsIgnoreCase("DocSum")) {
//                        currentResult.wrap();
//                        results.add(currentResult);
//                        currentResult = getNewESummaryResult();
//                    }
                    break;
            }
        }
        xmlr.closeCompletely();
        return currentResult;
    }


    public ESearchResult getNewESearchResult() {
        return new ESearchResult();
    }
}
