package uk.ac.ebi.metabolomes.webservices.eutils;

import java.io.InputStream;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: pmoreno
 * Date: 16/10/13
 * Time: 11:52
 * To change this template use File | Settings | File Templates.
 */
public class PubChemBioAssayESummaryXMLParserTest {

    /**
     * Aims to replicate and correct of the behaviour where titles and descriptions of PChem BioAssays where not loaded.
     *
     * @throws Exception
     */
    @Test
    public void testParseESummaryResultMissingTitleDesc() throws Exception {
        PubChemBioAssayESummaryXMLParser parser = new PubChemBioAssayESummaryXMLParser();
        InputStream in = PubChemBioAssayESummaryXMLParserTest.class
                .getResourceAsStream("esummary_pcassay_TitleDesc.xml");
        List<PubChemBioAssayESummaryResult> results = parser.parseESummaryResult(in);

        for (PubChemBioAssayESummaryResult res : results) {
            assertNotNull("Result is null",res);
            String name = res.getName();
            System.out.println("Name : "+name);
            assertTrue("Name has length zero",name.replaceAll("'","").trim().length()>0);
            assertNotNull("Description is null",res.getDescription());
            String desc = res.getDescription();
            System.out.println("Description : "+desc+"\n\n");
            assertTrue("Desc has length zero",desc.replaceAll("'","").trim().length()>0);
        }
    }
}
