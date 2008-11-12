/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface DBRecord
{

	/**
	 * Getter of the property <tt>dbLinks</tt>
	 * 
	 * @return Returns the dbLinks.
	 * 
	 */
	public Collection<DBAnnotation> getDbAnnotations();

	/**
	 * Getter of the property <tt>accession</tt>
	 * 
	 * @return Returns the accession.
	 * 
	 */
	public String getAccession();

	public ExternalDatabase getDatabase();

}