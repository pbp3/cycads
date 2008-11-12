/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

import java.util.Collection;

public interface DBAnnotation
{

	/**
	 * Getter of the property <tt>dBLinkNotes</tt>
	 * 
	 * @return Returns the dBLinkNotes.
	 * 
	 */
	public Collection<DBAnnotationNote> getDBAnnotationNotes();

	/**
	 * Getter of the property <tt>dBLinkMethod</tt>
	 * @return Returns the dBLinkMethod.
	 */
	public DBAnnotationMethod getDBAnnotationMethod();

	/**
	 * Getter of the property <tt>dBRecord</tt>
	 * 
	 * @return Returns the dBRecord.
	 * 
	 */
	public DBRecord getDBRecord();

}