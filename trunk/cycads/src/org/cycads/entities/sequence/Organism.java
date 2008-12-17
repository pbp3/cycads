/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

public interface Organism<SEQ extends Sequence< ? , ? , ? , ? , ? , ? >>
{

	public Collection<SEQ> getSequences();

	public Collection<SEQ> getSequences(double version);

	public SEQ createSequence(int seqId);

	public String getName();

	public int getId();

}