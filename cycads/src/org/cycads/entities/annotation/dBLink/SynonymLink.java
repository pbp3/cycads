/*
 * Created on 19/12/2008
 */
package org.cycads.entities.annotation.dBLink;

// It is the same that a synonym
public interface SynonymLink<S extends SynonymSource< ? , ? >, T extends DBRecord< ? , ? , ? , ? >>
{
	public S getSource();

	public T getTarget();
}
