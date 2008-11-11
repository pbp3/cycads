package org.cycads.entities.refact;

import java.util.Collection;

/** 
 */
public class DBAnnotationMethod extends AnnotationMethod
{
	private Collection<DBAnnotation>	dBAnnotations;

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return Returns the name.
	 * 
	 */
	public Collection<DBAnnotation> getDBAnnotations()
	{
		return dBAnnotations;
	}

}
