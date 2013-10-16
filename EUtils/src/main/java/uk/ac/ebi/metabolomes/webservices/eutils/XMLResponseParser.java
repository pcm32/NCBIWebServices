package uk.ac.ebi.metabolomes.webservices.eutils;

import org.codehaus.stax2.XMLStreamReader2;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pcm32
 * Date: 19/09/13
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class XMLResponseParser {
    private String parseIDFollowingIDTag(XMLStreamReader2 xmlr) throws XMLStreamException {
        int event;
        String id = "";
        boolean inId = false;
        loop1:
        while (xmlr.hasNext()) {
            event = xmlr.next();

            switch (event) {
                case XMLEvent.START_ELEMENT:
                    if (xmlr.getLocalName().equalsIgnoreCase("Id")) {
                        inId = true;
                    }
                    break;
                case XMLEvent.CHARACTERS:
                    if (inId) {
                        id = xmlr.getText();
                        inId = false;
                        break loop1;
                    }
                    break;
                default:
                    break;
            }
        }
        return id;
    }

    boolean hasAttributeNameWithValue(XMLStreamReader2 xmlr, String string) {
        for (int i = 0; i < xmlr.getAttributeCount(); i++) {
            if (xmlr.getAttributeLocalName(i).equalsIgnoreCase("Name") && xmlr.getAttributeValue(i).equalsIgnoreCase(string)) {
                return true;
            } else if (xmlr.getAttributeLocalName(i).equalsIgnoreCase("Name")) {
                return false;
            }
        }
        return false;
    }

    String getFollowingCharacters(XMLStreamReader2 xmlr) throws XMLStreamException {
        int event;
        String characters = null;
        loop1:
        while (xmlr.hasNext()) {
            event = xmlr.next();

            switch (event) {
                case XMLEvent.CHARACTERS:
                    return xmlr.getText();
                case XMLEvent.END_ELEMENT:
                    if (xmlr.getLocalName().equalsIgnoreCase("Item")) {
                        break loop1;
                    }
                    break;
            }
        }
        return characters;
    }

    protected List<String> parseList(XMLStreamReader2 xmlr) throws XMLStreamException {
        int event;
        List<String> listElements = new ArrayList<String>();
        boolean twoItemEndElementsInRow = false;
        boolean afterStartElement=false;
        loop1:
        while (xmlr.hasNext()) {
            event = xmlr.next();

            switch (event) {
                case XMLEvent.START_ELEMENT:
                    //LOGGER.debug("PL     Start Element: "+xmlr.getLocalName());
                    //LOGGER.debug("PL     Attributes: "+getAttributes(xmlr));
                    twoItemEndElementsInRow = false;
                    afterStartElement=true;
                    break;
                case XMLEvent.CHARACTERS:
                    //LOGGER.info("PL     Characters: "+xmlr.getText());
                    if (xmlr.getText().length() > 0 && afterStartElement) {
                        listElements.add(xmlr.getText());
                    }
                    afterStartElement=false;
                    break;
                case XMLEvent.END_ELEMENT:
                    //LOGGER.info("PL     End Element: "+xmlr.getLocalName());
                    if (xmlr.getLocalName().equalsIgnoreCase("Item") && twoItemEndElementsInRow) {
                        break loop1;
                    } else if (xmlr.getLocalName().equalsIgnoreCase("Item")) {
                        twoItemEndElementsInRow = true;
                    }
                    afterStartElement=false;
                    break;
            }
        }
        return listElements;
    }
}
