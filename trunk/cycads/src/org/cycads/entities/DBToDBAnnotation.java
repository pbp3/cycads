/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

public interface DBToDBAnnotation extends DBAnnotation
{

	/**
	 * Getter of the property <tt>source</tt>
	 * 
	 * @return Returns the source.
	 * 
	 */
	public DBRecord getSourceRecord();

}