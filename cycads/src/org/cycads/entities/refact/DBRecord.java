package org.cycads.entities.refact;

import java.util.Collection;

/** 
 */
public class DBRecord
{

	private ExternalDatabase			database;
	private String						accession;

	private Collection<DBLinkDBLink>	dbLinks;

	/**
	 * Getter of the property <tt>dbLinks</tt>
	 * 
	 * @return Returns the dbLinks.
	 * 
	 */
	public Collection<DBLinkDBLink> getDbLinks()
	{
		return dbLinks;
	}

	/**
	 * Getter of the property <tt>accession</tt>
	 * 
	 * @return Returns the accession.
	 * 
	 */
	public String getAccession()
	{
		return accession;
	}

}
