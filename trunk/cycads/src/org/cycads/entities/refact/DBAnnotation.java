package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.annotation.dBLink.DBLinkAnnotation;
import org.cycads.entities.annotation.dBLink.DBRecord;

public abstract class DBAnnotation implements DBLinkAnnotation
{

	private Collection<DBAnnotationNote>	dBAnnotationNotes;
	private DBAnnotationMethod				dBAnnotationMethod;
	private DBRecord						dBRecord;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBAnnotation#getDBAnnotationNotes()
	 */
	public Collection<DBAnnotationNote> getDBAnnotationNotes()
	{
		return dBAnnotationNotes;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBAnnotation#getDBAnnotationMethod()
	 */
	public DBAnnotationMethod getDBAnnotationMethod()
	{
		return dBAnnotationMethod;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBAnnotation#getDBRecord()
	 */
	public DBRecord getDBRecord()
	{
		return dBRecord;
	}

}
