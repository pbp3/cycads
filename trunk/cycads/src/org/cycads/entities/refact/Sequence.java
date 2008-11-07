package org.cycads.entities.refact;

import java.util.Collection;

/** 
 */
public class Sequence
{

	private String						name;
	private String						description;
	private double						version;

	private Collection<SequenceDBLink>	dBLinks;
	private Collection<Feature>			features;

	/**
	 * Getter of the property <tt>dBLinks</tt>
	 * 
	 * @return Returns the dBLinks.
	 * 
	 */
	public Collection<SequenceDBLink> getDBLinks()
	{
		return dBLinks;
	}

	/**
	 * Returns <tt>true</tt> if this collection contains all of the elements in the specified collection.
	 * 
	 * @param elements collection to be checked for containment in this collection.
	 * @see java.util.Collection#containsAll(Collection)
	 * 
	 */
	public boolean containsAllDBLinks(Collection<SequenceDBLink> dBLinks)
	{
		return this.dBLinks.containsAll(dBLinks);
	}

	/**
	 * Getter of the property <tt>sequenceFeatures</tt>
	 * 
	 * @return Returns the sequenceFeatures.
	 * 
	 */
	public Collection<Feature> getFeatures()
	{
		return features;
	}

	/**
	 * Getter of the property <tt>description</tt>
	 * 
	 * @return Returns the description.
	 * 
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Getter of the property <tt>version</tt>
	 * 
	 * @return Returns the version.
	 * 
	 */
	public double getVersion()
	{
		return version;
	}

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return Returns the name.
	 * 
	 */
	public String getName()
	{
		return name;
	}

}