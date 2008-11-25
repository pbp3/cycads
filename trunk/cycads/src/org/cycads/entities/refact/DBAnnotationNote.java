package org.cycads.entities.refact;

import org.cycads.entities.annotation.ExternalAnnotation;
import org.cycads.entities.annotation.ExternalAnnotation;

/*
 * Created on 07/11/2008
 */

public class DBAnnotationNote extends Note implements DBAnnotationNote
{

	private ExternalAnnotation	externalAnnotation;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBAnnotationNote#getDBAnnotation()
	 */
	public ExternalAnnotation getDBAnnotation()
	{
		return externalAnnotation;
	}

}
