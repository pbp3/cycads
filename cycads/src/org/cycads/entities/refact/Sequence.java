package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.SequenceToDBAnnotation;
import org.cycads.entities.sequence.feature.Feature;

/**
 */
public class Sequence implements Sequence
{

	private String								name;
	private String								description;
	private double								version;
	private Organism							organism;

	private Collection<SequenceToDBAnnotation>	dBLinks;
	private Collection<Feature>				features;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.ISequence#getDBLinks()
	 */
	public Collection<SequenceToDBAnnotation> getDBLinks() {
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
	public Organism getOrganism() {
		return organism;
	}

}