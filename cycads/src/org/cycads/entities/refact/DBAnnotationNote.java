package org.cycads.entities.refact;

import org.cycads.entities.annotation.DBLink;
import org.cycads.entities.annotation.DBLink;

/*
 * Created on 07/11/2008
 */

public class DBAnnotationNote extends Note implements DBAnnotationNote
{

	private DBLink	dBLink;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBAnnotationNote#getDBAnnotation()
	 */
	public DBLink getDBAnnotation()
	{
		return dBLink;
	}

}
