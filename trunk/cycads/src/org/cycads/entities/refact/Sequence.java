package org.cycads.entities.refact;

import java.util.Collection;

/** 
 */
public class Sequence
{

	private String						name;
	private String						description;
	private double						version;
	private Organism					organism;

	private Collection<SequenceToDBAnnotation>	dBLinks;
	private Collection<Feature>			features;

	/**
	 * Getter of the property <tt>dBLinks</tt>
	 * 
	 * @return Returns the dBLinks.
	 * 
	 */
	public Collection<SequenceToDBAnnotation> getDBLinks()
	{
		return dBLinks;
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

	public Organism getOrganism()
	{
		return organism;
	}

}