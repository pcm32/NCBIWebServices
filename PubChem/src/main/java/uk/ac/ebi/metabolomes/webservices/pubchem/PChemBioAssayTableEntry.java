package uk.ac.ebi.metabolomes.webservices.pubchem;

import uk.ac.ebi.mdk.domain.identifier.PubChemCompoundIdentifier;
import uk.ac.ebi.mdk.domain.identifier.PubChemSubstanceIdentifier;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pcm32
 * Date: 20/09/13
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public class PChemBioAssayTableEntry {

    private String SID;
    private String CID;
    private PChemBioAssayTable.Outcome outcome;
    private Integer activityScore;
    private List<String> additionalFields = new LinkedList<String>();
    private PubChemCompoundIdentifier compoundIdentifier;
    private PubChemSubstanceIdentifier substanceIdentifier;

    public void setSID(String SID) {
        this.SID = SID;
        substanceIdentifier = new PubChemSubstanceIdentifier(SID);
    }

    public void setCID(String CID) {
        this.CID = CID;
        compoundIdentifier = new PubChemCompoundIdentifier(CID);
    }

    public void setOutcome(String outcome) {
        this.outcome = PChemBioAssayTable.Outcome.valueOf(outcome);
    }

    public void setActivityScore(String activityScore) {
        try {
            this.activityScore = Integer.parseInt(activityScore);
        } catch (NumberFormatException e) {
            this.activityScore = null;
        }
    }

    public void setAdditionalFields(String... fields) {
        additionalFields.addAll(Arrays.asList(fields));
    }

    public String getCID() {
        return CID;
    }

    public String getSID() {
        return SID;
    }

    public boolean isActive() {
        return outcome.equals(PChemBioAssayTable.Outcome.Active);
    }

    public PubChemCompoundIdentifier getPChemCompoundIdentifier() {
        return this.compoundIdentifier;
    }

    public String getAdditionalField(Integer index) {
        return additionalFields.get(index);
    }
}
