/*
 * Created on 19/12/2008
 */
package org.cycads.entities.annotation.dBLink;

public interface ExternalSynonymFilter<E extends ExternalSynonym< ? , ? >>
{
	public boolean accept(E synonym);
}
