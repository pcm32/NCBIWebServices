package uk.ac.ebi.metabolomes.webservices.eutils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: pcm32
 * Date: 19/09/13
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public class ESearchResult {

    Integer count;
    Integer retMax;
    Integer retStart;
    Set<String> resultIdentifiers;

    public ESearchResult() {
        resultIdentifiers = new HashSet<String>();
    }

    public void setCount(String count) {
        this.count = Integer.parseInt(count);
    }

    public void setRetMax(String retMax) {
        this.retMax = Integer.parseInt(retMax);
    }

    public void setRetStart(String retStart) {
        this.retStart = Integer.parseInt(retStart);
    }

    public void addId(String id) {
        resultIdentifiers.add(id);
    }

    public Set<String> getIds() {
        return resultIdentifiers;
    }

    public boolean resultsMissing() {
        return count > retMax + retStart;
    }

    public Integer getNextStart() {
        return retMax + retStart;
    }
}
