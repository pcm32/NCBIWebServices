package uk.ac.ebi.metabolomes.webservices.pubchem;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pcm32
 * Date: 20/09/13
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
public class PChemBioAssayTableParser {

    private static final Logger LOGGER = Logger.getLogger(PChemBioAssayTableParser.class);


    public PChemBioAssayTable parse(InputStream csvInputStream) {
        CSVReader reader = new CSVReader(new InputStreamReader(csvInputStream));

        PChemBioAssayTable table = new PChemBioAssayTable();


        try {
            String[] headerAux = reader.readNext();
            List<String> header = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(headerAux,4,headerAux.length)));

            table.setAdditionalFieldsHeader(header);

            String[] line;
            while ((line = reader.readNext())!= null) {
                PChemBioAssayTableEntry entry = new PChemBioAssayTableEntry();
                entry.setSID(line[0]);
                entry.setCID(line[1]);
                entry.setOutcome(line[2]);
                entry.setActivityScore(line[3]);
                entry.setAdditionalFields(Arrays.copyOfRange(line,4,line.length));

                table.addEntry(entry);
            }
        } catch (IOException e) {
            LOGGER.error("Could not read CSV on input stream, empty table returned: ",e);
        }

        return table;
    }
}
