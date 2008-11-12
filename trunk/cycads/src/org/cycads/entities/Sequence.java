/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

import java.util.Collection;

public interface Sequence
{

	/**
	 * Getter of the property <tt>dBLinks</tt>
	 * 
	 * @return Returns the dBLinks.
	 * 
	 */
	public Collection<SequenceToDBAnnotation> getDBLinks();

	/**
	 * Getter of the property <tt>sequenceFeatures</tt>
	 * 
	 * @return Returns the sequenceFeatures.
	 * 
	 */
	public Collection<Feature> getFeatures();

	/**
	 * Getter of the property <tt>description</tt>
	 * 
	 * @return Returns the description.
	 * 
	 */
	public String getDescription();

	/**
	 * Getter of the property <tt>version</tt>
	 * 
	 * @return Returns the version.
	 * 
	 */
	public double getVersion();

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return Returns the name.
	 * 
	 */
	public String getName();

	public Organism getOrganism();

}