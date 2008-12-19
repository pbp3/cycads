/*
 * Created on 19/12/2008
 */
package org.cycads.entities.annotation.dBLink;

public interface ExternalSynonymSource<E extends ExternalSynonym< ? , ? >, T extends DBRecord< ? , ? , ? , ? >>
{
	public E createExternalSynonym(T target);
}
