package uk.ac.ebi.metabolomes.webservices.pubchem;

import uk.ac.ebi.mdk.domain.identifier.PubChemCompoundIdentifier;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: pcm32
 * Date: 20/09/13
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
public class PChemBioAssayTable {
    private List<PChemBioAssayTableEntry> entries;
    private Map<PubChemCompoundIdentifier,PChemBioAssayTableEntry> entriesIndexCID;
    private Set<PubChemCompoundIdentifier> activeCompounds;
    private List<String> additionalFieldsHeader;

    private String AID;

    /**
     * Annotated meta data.
     */
    private String description = "";
    private String name = "";
    private List<String> sources = new ArrayList<String>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    /**
     * Sets the description of the bio assay.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the ID of the bio assay, which in the case of PubChem corresponds to its BioAssay ID (AID).
     *
     * @return the id as a String
     */
    public String getID() {
        return AID;
    }

    public void setAdditionalFieldsHeader(List<String> additionalFieldsHeader) {
        this.additionalFieldsHeader = additionalFieldsHeader;
    }

    public String getDescription() {
        return description;
    }

    public enum Outcome {
        Active, Inactive, Unspecified, Inconclusive;
    }

    public PChemBioAssayTable() {
        entries = new ArrayList<PChemBioAssayTableEntry>();
        entriesIndexCID = new HashMap<PubChemCompoundIdentifier, PChemBioAssayTableEntry>();
        activeCompounds = new HashSet<PubChemCompoundIdentifier>();
    }

    public void addEntry(PChemBioAssayTableEntry entry) {
        this.entries.add(entry);
        this.entriesIndexCID.put(entry.getPChemCompoundIdentifier(),entry);
        if (entry.isActive()) {
            activeCompounds.add(entry.getPChemCompoundIdentifier());
        }
    }

    public int getEntryCount() {
        return entries.size();
    }

    public Set<PubChemCompoundIdentifier> getActiveCompounds() {
        return activeCompounds;
    }

    public List<PChemBioAssayTableEntry> getEntries() {
        return this.entries;
    }

    public Integer getAdditionalFieldIndex(String headerName) {
        return additionalFieldsHeader.indexOf(headerName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PChemBioAssayTable that = (PChemBioAssayTable) o;

        if (AID != null ? !AID.equals(that.AID) : that.AID != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return AID != null ? AID.hashCode() : 0;
    }
}
