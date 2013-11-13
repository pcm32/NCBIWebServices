package uk.ac.ebi.mdk.domain.identifier;

/**
 * A simple Identifier for the PubChem BioAssay object.
 *
 * Created with IntelliJ IDEA.
 * User: pmoreno
 * Date: 13/11/13
 * Time: 17:00
 * To change this template use File | Settings | File Templates.
 */
public class PubChemBioAssayIdentifier extends AbstractIdentifier {

    public PubChemBioAssayIdentifier(String aid) {
        super();
        this.setAccession(aid);
    }

    public PubChemBioAssayIdentifier() {
        super();
    }

    @Override
    public Identifier newInstance() {
        return new PubChemBioAssayIdentifier();
    }
}
