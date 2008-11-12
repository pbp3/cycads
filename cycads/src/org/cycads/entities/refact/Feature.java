package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.annotation.DBAnnotationMethod;
import org.cycads.entities.annotation.ExternalDatabase;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.feature.Feature;
import org.cycads.entities.sequence.feature.FeatureAnnotationMethod;
import org.cycads.entities.sequence.feature.FeatureNote;
import org.cycads.entities.sequence.feature.FeatureToDBAnnotation;
import org.cycads.entities.sequence.feature.FeatureType;
import org.cycads.entities.sequence.feature.Location;

/**
 */
public class Feature implements Feature
{
	private String								name;
	private Location							location;
	private FeatureAnnotationMethod			method;
	private FeatureType						featureType;

	private Collection<FeatureNote>			notes;

	private Collection<FeatureToDBAnnotation>	dBAnnotations;

	private Sequence							sequence;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#getSequence()
	 */
	public Sequence getSequence() {
		return sequence;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#getLoaction()
	 */
	public Location getLoaction() {
		return location;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#getMethod()
	 */
	public FeatureAnnotationMethod getMethod() {
		return method;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#getNotes()
	 */
	public Collection<FeatureNote> getNotes() {
		return notes;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#addNote(org.cycads.entities.refact.IFeatureNote)
	 */
	public void addNote(FeatureNote note) {
		notes.add(note);
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#addNote(java.lang.String, java.lang.String)
	 */
	public FeatureNote addNote(String value, String noteType) {
		//		notes.add(new FeatureNote(value,noteType));
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#getdBAnnotations()
	 */
	public Collection<FeatureToDBAnnotation> getdBAnnotations() {
		return dBAnnotations;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#addDBAnnotation(org.cycads.entities.refact.IFeatureToDBAnnotation)
	 */
	public void addDBAnnotation(FeatureToDBAnnotation featureToDBAnnotation) {
		dBAnnotations.add(featureToDBAnnotation);
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#addDBAnnotation(org.cycads.entities.refact.IExternalDatabase, java.lang.String, org.cycads.entities.refact.IDBAnnotationMethod)
	 */
	public FeatureToDBAnnotation addDBAnnotation(ExternalDatabase db, String accession, DBAnnotationMethod method) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#addDBAnnotation(java.lang.String, java.lang.String, org.cycads.entities.refact.IDBAnnotationMethod)
	 */
	public FeatureToDBAnnotation addDBAnnotation(String db, String accession, DBAnnotationMethod method) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#getFeatureType()
	 */
	public FeatureType getFeatureType() {
		return featureType;
	}

}
