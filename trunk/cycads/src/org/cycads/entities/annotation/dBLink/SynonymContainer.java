/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import java.util.Collection;

// public interface DBLinkContainer<S extends DBLinkSource< ? , ? >, R extends DBRecord< ? >>
public interface SynonymContainer<D extends SynonymLink< ? , ? >, S extends DBLinkAnnotSource< ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >>
{
	public void addSynonym(D synonym);

	public D getDBLink(S source, R target);

	public Collection<D> getDBLinks(R target);

	public Collection<D> getDBLinks(String dbName, String accession);

	public Collection<D> getDBLinks(String dbName);

	public Collection<D> getDBLinks(SynonymFilter<D> filter);

}
