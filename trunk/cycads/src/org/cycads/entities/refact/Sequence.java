package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.Feature;
import org.cycads.entities.IOrganism;
import org.cycads.entities.ISequence;
import org.cycads.entities.ISequenceToDBAnnotation;

/**
 */
public class Sequence implements ISequence
{

	private String								name;
	private String								description;
	private double								version;
	private IOrganism							organism;

	private Collection<ISequenceToDBAnnotation>	dBLinks;
	private Collection<Feature>				features;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.ISequence#getDBLinks()
	 */
	public Collection<ISequenceToDBAnnotation> getDBLinks() {
		return dBLinks;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.ISequence#getFeatures()
	 */
	public Collection<Feature> getFeatures() {
		return features;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.ISequence#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.ISequence#getVersion()
	 */
	public double getVersion() {
		return version;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.ISequence#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.ISequence#getOrganism()
	 */
	public IOrganism getOrganism() {
		return organism;
	}

}