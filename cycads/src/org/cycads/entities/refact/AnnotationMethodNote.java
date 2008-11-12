/*
 * Created on 08/11/2008
 */
package org.cycads.entities.refact;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.AnnotationMethodNote;

public class AnnotationMethodNote extends Note implements AnnotationMethodNote
{
	private AnnotationMethod	method;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IAnnotationMethodNote#getMethod()
	 */
	public AnnotationMethod getMethod()
	{
		return method;
	}

}
