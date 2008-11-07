package org.cycads.entities.refact;

import java.util.Collection;

public abstract class DBLink
{

	private Collection<DBLinkNote>	dBLinkNotes;
	private DBLinkMethod			dBLinkMethod;
	private DBRecord				dBRecord;

	/**
	 * Getter of the property <tt>dBLinkNotes</tt>
	 * 
	 * @return Returns the dBLinkNotes.
	 * 
	 */
	public Collection<DBLinkNote> getDBLinkNotes()
	{
		return dBLinkNotes;
	}

	/**
	 * Getter of the property <tt>dBLinkMethod</tt>
	 * @return Returns the dBLinkMethod.
	 */
	public DBLinkMethod getDBLinkMethod()
	{
		return dBLinkMethod;
	}

	/**
	 * Getter of the property <tt>dBRecord</tt>
	 * 
	 * @return Returns the dBRecord.
	 * 
	 */
	public DBRecord getDBRecord()
	{
		return dBRecord;
	}

}
