package org.cycads.entities.refact;

import java.util.Collection;

public abstract class DBAnnotation
{

	private Collection<DBAnnotationNote>	dBAnnotationNotes;
	private DBAnnotationMethod				dBAnnotationMethod;
	private DBRecord						dBRecord;

	/**
	 * Getter of the property <tt>dBLinkNotes</tt>
	 * 
	 * @return Returns the dBLinkNotes.
	 * 
	 */
	public Collection<DBAnnotationNote> getDBAnnotationNotes()
	{
		return dBAnnotationNotes;
	}

	/**
	 * Getter of the property <tt>dBLinkMethod</tt>
	 * @return Returns the dBLinkMethod.
	 */
	public DBAnnotationMethod getDBAnnotationMethod()
	{
		return dBAnnotationMethod;
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
