package org.cycads.entities.refact;

import java.util.Collection;

/**
 */
public class Feature
{
	private String						name;
	private Location					location;
	private FeatureMethod		method;

	private Collection<FeatureNote>		notes;

	private Collection<FeatureDBLink>	dBLinks;

	private Sequence					sequence;

	/**
	 * Getter of the property <tt>sequence</tt>
	 * 
	 * @return Returns the sequence.
	 * 
	 */
	public Sequence getSequence()
	{
		return sequence;
	}

	public Location getLoaction()
	{
		return location;
	}

	public FeatureMethod getMethod()
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

	public Collection<FeatureDBLink> getdBLinks()
	{
		return dBLinks;
	}

}
