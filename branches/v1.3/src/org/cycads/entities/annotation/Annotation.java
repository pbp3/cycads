/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.BasicEntity;

public interface Annotation<SO extends BasicEntity, TA extends BasicEntity> extends Association<SO, TA>
{
	public static final String	ENTITY_TYPE_NAME	= "Annotation";

	public AnnotationMethod getAnnotationMethod();

	public Collection< ? extends BasicEntity> getParents();

	public void addParent(BasicEntity parent);

	public void setScore(String score);

	public String getScore();

}