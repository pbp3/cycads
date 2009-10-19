/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.EntityObject;

public interface Annotation<SO, TA> extends Association<SO, TA>, EntityObject
{
	public static final String	OBJECT_TYPE_NAME	= "Annotation";

	public AnnotationMethod getAnnotationMethod();

	public Collection<Annotation< ? , ? >> getParents();

	public void addParent(Annotation< ? , ? > parent);

	public void setScore(String score);

	public String getScore();

}