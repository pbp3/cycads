/*
 * Created on 08/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

public class AnnotationMethod
{
	private String								name;
	private Collection<AnnotationMethodLabel>	labels;
	private Collection<EntityType>				sourceTypes;
	private Collection<EntityType>				targetTypes;

	public void addSourceType(EntityType sourceType)
	{

	}

	public void addTargetType(EntityType targetType)
	{

	}

	public Collection<EntityType> getSourceTypes()
	{
		return sourceTypes;
	}

	public Collection<EntityType> getTargetTypes()
	{
		return targetTypes;
	}

	public String getName()
	{
		return name;
	}

	public Collection<AnnotationMethodLabel> getLabels()
	{
		return labels;
	}

	public void addLabel(AnnotationMethodLabel label)
	{

	}

}
