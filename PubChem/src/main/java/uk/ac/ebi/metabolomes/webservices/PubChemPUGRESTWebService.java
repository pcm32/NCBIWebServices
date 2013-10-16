package uk.ac.ebi.metabolomes.webservices;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.log4j.Logger;
import uk.ac.ebi.metabolomes.webservices.pubchem.PChemBioAssayTable;
import uk.ac.ebi.metabolomes.webservices.pubchem.PChemBioAssayTableParser;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Created with IntelliJ IDEA.
 * User: pcm32
 * Date: 19/09/13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class PubChemPUGRESTWebService extends NCBIJerseyBasedWebService {
    private static final Logger LOGGER = Logger.getLogger(PubChemPUGRESTWebService.class);
    private Client client;

    private final String baseURL = "http://uk.ac.ebi.metabolomes.webservices.pubchem.ncbi.nlm.nih.gov/rest/pug/";

    public enum PubChemDomains {
        assay;
    }

    public PubChemPUGRESTWebService() {
        client = Client.create();
    }

    public PChemBioAssayTable getBioAssayTable(String AID) {
        WebResource webRes = client.resource(baseURL + PubChemDomains.assay.toString() + "/aid/" + AID + "/csv");
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        ClientResponse resp = submitPost(webRes, queryParams);

        if (resp.getStatus() != 200) {
            throw new RuntimeException("Failed for AID "+AID+": HTTP error code : "
                    + resp.getStatus());
        }

        PChemBioAssayTableParser parser = new PChemBioAssayTableParser();
        PChemBioAssayTable table = parser.parse(resp.getEntityInputStream());
        table.setAID(AID);

        return table;
    }

}
