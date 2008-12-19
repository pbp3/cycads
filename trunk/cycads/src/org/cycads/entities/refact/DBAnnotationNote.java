package org.cycads.entities.refact;

import org.cycads.entities.annotation.dBLink.DBLinkAnnotation;

/*
 * Created on 07/11/2008
 */

public class DBAnnotationNote extends Note implements DBAnnotationNote
{

	private DBLinkAnnotation	dBLink;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBAnnotationNote#getDBAnnotation()
	 */
	public DBLinkAnnotation getDBAnnotation()
	{
		return dBLink;
	}

}
