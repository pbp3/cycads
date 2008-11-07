package org.cycads.entities.refact;

import java.util.Collection;

public class DBLink
{

	private Collection<DBLinkNote>	dBLinkNote;
	private DBLinkMethod			dBLinkMethod;
	private DBRecord				dBRecord;

	/**
	 * Getter of the property <tt>dBLinkNote</tt>
	 * 
	 * @return Returns the dBLinkNote.
	 * 
	 */
	public Collection<DBLinkNote> getDBLinkNote()
	{
		return dBLinkNote;
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
