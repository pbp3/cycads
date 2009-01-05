/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

public interface Organism<SEQ extends Sequence< ? , ? , ? , ? >>
{

	public Collection<SEQ> getSequences();

	public Collection<SEQ> getSequences(double version);

	public String getName();

	public int getId();

	public SEQ getOrCreateSequence(String seqDatabase, String seqAccession);

	public SEQ getSequence(String seqDatabase, String seqAccession);

}