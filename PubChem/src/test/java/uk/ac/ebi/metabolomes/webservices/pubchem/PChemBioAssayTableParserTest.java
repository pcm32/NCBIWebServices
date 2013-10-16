package uk.ac.ebi.metabolomes.webservices.pubchem;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: pcm32
 * Date: 20/09/13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class PChemBioAssayTableParserTest {
    @Test
    public void testParse() throws Exception {
        PChemBioAssayTableParser parser = new PChemBioAssayTableParser();
        PChemBioAssayTable table = parser.parse(PChemBioAssayTableParser.class.getResourceAsStream("bioAssayExample.csv"));

        assertEquals(16,table.getEntryCount());
    }
}
