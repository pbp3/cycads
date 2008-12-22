package org.cycads.entities.refact;

import org.cycads.entities.annotation.dBLink.OntologyAnnot;

/*
 * Created on 07/11/2008
 */

public class DBAnnotationNote extends Note implements DBAnnotationNote
{

	private OntologyAnnot	dBLink;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBAnnotationNote#getDBAnnotation()
	 */
	public OntologyAnnot getDBAnnotation()
	{
		return dBLink;
	}

}
