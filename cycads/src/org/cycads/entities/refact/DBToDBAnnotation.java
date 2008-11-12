package org.cycads.entities.refact;

import org.cycads.entities.DBRecord;
import org.cycads.entities.DBToDBAnnotation;

/*
 * Created on 07/11/2008
 */

public class DBToDBAnnotation extends DBAnnotation implements DBToDBAnnotation
{

	private DBRecord	source;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBToDBAnnotation#getSource()
	 */
	public DBRecord getSourceRecord() {
		return source;
	}

}
