package org.cycads.entities.refact;

import org.cycads.entities.annotation.AnnotOntology;

/*
 * Created on 07/11/2008
 */

public class DBAnnotationNote extends Note implements DBAnnotationNote
{

	private AnnotOntology	dBLink;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBAnnotationNote#getDBAnnotation()
	 */
	public AnnotOntology getDBAnnotation()
	{
		return dBLink;
	}

}
