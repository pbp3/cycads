/*
 * Created on 19/12/2008
 */
package org.cycads.entities.annotation.dBLink;

public interface SynonymSource<E extends SynonymLink< ? , ? >, T extends DBRecord< ? , ? , ? , ? >>
{
	public E createDBLink(T target);
}
