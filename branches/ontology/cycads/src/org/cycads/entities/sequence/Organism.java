/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.dBLink.DBLinkAnnot;

public interface Organism<SEQ extends Sequence< ? , ? , ? , ? , ? , ? >>
{

	public Collection<SEQ> getSequences();

	public Collection<SEQ> getSequences(double version);

	public String getName();

	public int getId();

	public SEQ getOrCreateSequence(String seqAccession, int version);

	public Collection<DBLinkAnnot< ? , SEQ, ? , ? >> getSequenceDBLinks(String seqDatabase, String seqAccession);

}