/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

import java.util.Collection;

public interface DBAnnotationMethod extends AnnotationMethod
{

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return Returns the name.
	 * 
	 */
	public Collection<DBAnnotation> getDBAnnotations();

}