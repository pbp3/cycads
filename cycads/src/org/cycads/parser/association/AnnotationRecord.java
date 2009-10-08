/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.entities.annotation.AnnotationMethod;

public interface AnnotationRecord<S, T> extends AssociationRecord<S, T>
{
	public String getScore();

	public AnnotationMethod getMethod();
}
