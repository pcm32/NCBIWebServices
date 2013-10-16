package uk.ac.ebi.metabolomes.webservices;

import org.junit.Test;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: pcm32
 * Date: 19/09/13
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
public class EUtilsWebServiceConnectionTest {
    @Test
    public void testGetPubChemBioAssaysForTermSearch() throws Exception {
        EUtilsWebServiceConnection connection = new EUtilsWebServiceConnection();

        Set<String> identifiers = connection.getPubChemBioAssaysForTermSearch("autophagy");

        System.out.println("Autophagy PC Assays : "+identifiers.size());
    }
}
