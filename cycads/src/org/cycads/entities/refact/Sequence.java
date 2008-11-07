package org.cycads.entities.refact;

import java.util.Collection;
import java.util.Iterator;

/** 
 */
public class Sequence
{

	/**
	 * 
	 */
	private Collection<SequenceDBLink>	dBLinks;

	/**
	 * Getter of the property <tt>dBLinks</tt>
	 * 
	 * @return Returns the dBLinks.
	 * 
	 */
	public Collection<SequenceDBLink> getDBLinks() {
		return dBLinks;
	}

	/**
	 * Returns <tt>true</tt> if this collection contains all of the elements in the specified collection.
	 * 
	 * @param elements collection to be checked for containment in this collection.
	 * @see java.util.Collection#containsAll(Collection)
	 * 
	 */
	public boolean containsAllDBLinks(Collection<SequenceDBLink> dBLinks) {
		return this.dBLinks.containsAll(dBLinks);
	}

	/**
	 * 
	 */
	private Collection<SequenceFeature>	sequenceFeatures;

	/**
	 * Getter of the property <tt>sequenceFeatures</tt>
	 * 
	 * @return Returns the sequenceFeatures.
	 * 
	 */
	public Collection<SequenceFeature> getSequenceFeatures() {
		return sequenceFeatures;
	}

	/*
	 * (non-javadoc)
	 */
	private String	description;

	/**
	 * Getter of the property <tt>description</tt>
	 * 
	 * @return Returns the description.
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * (non-javadoc)
	 */
	private double	version;

	/**
	 * Getter of the property <tt>version</tt>
	 * 
	 * @return Returns the version.
	 * 
	 */
	public double getVersion() {
		return version;
	}

	/*
	 * (non-javadoc)
	 */
	private String	name;

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return Returns the name.
	 * 
	 */
	public String getName() {
		return name;
	}

}

///**
// * Getter of the property <tt>sequenceFeatures</tt>
// *
// * @return Returns the sequenceFeatures.
// * 
// */
//public SequenceFeature getSequenceFeature()
//{
//	return sequenceFeatures;
//}
///**
// * Setter of the property <tt>sequenceFeatures</tt>
// *
// * @param sequenceFeatures The sequenceFeatures to set.
// *
// */
//public void setSequenceFeature(SequenceFeature sequenceFeatures ){
//	this.sequenceFeature = sequenceFeatures;
//}