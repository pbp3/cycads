package org.cycads.entities.refact;

import org.cycads.entities.annotation.dBLink.DBLinkAnnot;

/*
 * Created on 07/11/2008
 */

public class DBAnnotationNote extends Note implements DBAnnotationNote
{

	private DBLinkAnnot	dBLink;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBAnnotationNote#getDBAnnotation()
	 */
	public DBLinkAnnot getDBAnnotation()
	{
		return dBLink;
	}

}
