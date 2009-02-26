/*
 * Created on 19/12/2008
 */
package org.cycads.entities.annotation.dBLink;

public interface SynonymFilter<E extends SynonymLink< ? , ? >>
{
	public boolean accept(E synonym);
}
