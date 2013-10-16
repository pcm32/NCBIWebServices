package uk.ac.ebi.metabolomes.webservices;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Created with IntelliJ IDEA.
 * User: pcm32
 * Date: 20/09/13
 * Time: 10:40
 * To change this template use File | Settings | File Templates.
 */
public class NCBIJerseyBasedWebService {

    private static final Logger LOGGER = Logger.getLogger(NCBIJerseyBasedWebService.class);

    public final Integer MAX_RECORDS_PER_QUERY = 5000;
    private Long previousCallTime;

    /**
     * Checks whether the required delay has been waited or not, and waits if necessary until the required time has passed.
     */
    void checkAndWaitForRequiredDelay() {
        Long currentCallTime = System.currentTimeMillis();
        try {
            while (previousCallTime != null && currentCallTime - previousCallTime < 3000) {
                Thread.sleep(3000);
                currentCallTime = System.currentTimeMillis();
                LOGGER.info("Waiting for required EUtils delay time...");
            }
        } catch (InterruptedException e) {
            LOGGER.warn("Problems in waiting for delay to avoid doing queries within 3 secs.");
        }
    }

    /**
     * Posts to the EUtils server should always be submitted by this method, which will check whether the required waiting
     * time has been respected. This is currently not multithread safe.
     *
     * @param webRes
     * @param queryParams
     * @return
     * @throws com.sun.jersey.api.client.UniformInterfaceException
     */
    ClientResponse submitPost(WebResource webRes, MultivaluedMap queryParams) throws UniformInterfaceException {
        checkAndWaitForRequiredDelay();
        ClientResponse resp = webRes.post(ClientResponse.class,queryParams);
        previousCallTime = System.currentTimeMillis();
        return resp;
    }
}
