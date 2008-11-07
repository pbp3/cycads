package org.cycads.entities.refact;

import java.util.Collection;
import java.util.Iterator;/*
 * Created on 07/11/2008
 */

public class DBLink
{

	/**
	 * 
	 */
	private Collection<DBLinkNote>	dBLinkNote;

	/**
	 * Getter of the property <tt>dBLinkNote</tt>
	 * 
	 * @return Returns the dBLinkNote.
	 * 
	 */
	public Collection<DBLinkNote> getDBLinkNote() {
		return dBLinkNote;
	}

	/*
	* (non-javadoc)
	*/
	private DBLinkMethod	dBLinkMethod	= null;

	/**
	 * Getter of the property <tt>dBLinkMethod</tt>
	 * @return Returns the dBLinkMethod.
	 */
	public DBLinkMethod getDBLinkMethod() {
		return dBLinkMethod;
	}

	/*
	 * (non-javadoc)
	 */
	private DBRecord	dBRecord	= null;

	/**
	 * Getter of the property <tt>dBRecord</tt>
	 * 
	 * @return Returns the dBRecord.
	 * 
	 */
	public DBRecord getDBRecord() {
		return dBRecord;
	}

}
