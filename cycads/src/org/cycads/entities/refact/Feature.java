package org.cycads.entities.refact;

import java.util.Collection;

/**
 */
public class Feature
{
	private String								name;
	private Location							location;
	private FeatureAnnotationMethod				method;

	private Collection<FeatureNote>				notes;

	private Collection<FeatureToDBAnnotation>	dBAnnotations;

	private Sequence							sequence;

	public Sequence getSequence()
	{
		return sequence;
	}

	public Location getLoaction()
	{
		return location;
	}

	public FeatureAnnotationMethod getMethod()
	{
		return method;
	}

	public Collection<FeatureNote> getNotes()
	{
		return notes;
	}

	public String getName()
	{
		return name;
	}

	public Collection<FeatureToDBAnnotation> getdBAnnotations()
	{
		return dBAnnotations;
	}

}
