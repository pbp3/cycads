package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.DBAnnotationMethod;
import org.cycads.entities.ExternalDatabase;
import org.cycads.entities.Feature;
import org.cycads.entities.IFeatureAnnotationMethod;
import org.cycads.entities.IFeatureNote;
import org.cycads.entities.IFeatureToDBAnnotation;
import org.cycads.entities.IFeatureType;
import org.cycads.entities.ILocation;
import org.cycads.entities.ISequence;

/**
 */
public class Feature implements Feature
{
	private String								name;
	private ILocation							location;
	private IFeatureAnnotationMethod			method;
	private IFeatureType						featureType;

	private Collection<IFeatureNote>			notes;

	private Collection<IFeatureToDBAnnotation>	dBAnnotations;

	private ISequence							sequence;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#getSequence()
	 */
	public ISequence getSequence() {
		return sequence;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#getLoaction()
	 */
	public ILocation getLoaction() {
		return location;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#getMethod()
	 */
	public IFeatureAnnotationMethod getMethod() {
		return method;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#getNotes()
	 */
	public Collection<IFeatureNote> getNotes() {
		return notes;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#addNote(org.cycads.entities.refact.IFeatureNote)
	 */
	public void addNote(IFeatureNote note) {
		notes.add(note);
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#addNote(java.lang.String, java.lang.String)
	 */
	public IFeatureNote addNote(String value, String noteType) {
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
	public Collection<IFeatureToDBAnnotation> getdBAnnotations() {
		return dBAnnotations;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#addDBAnnotation(org.cycads.entities.refact.IFeatureToDBAnnotation)
	 */
	public void addDBAnnotation(IFeatureToDBAnnotation featureToDBAnnotation) {
		dBAnnotations.add(featureToDBAnnotation);
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#addDBAnnotation(org.cycads.entities.refact.IExternalDatabase, java.lang.String, org.cycads.entities.refact.IDBAnnotationMethod)
	 */
	public IFeatureToDBAnnotation addDBAnnotation(ExternalDatabase db, String accession, DBAnnotationMethod method) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#addDBAnnotation(java.lang.String, java.lang.String, org.cycads.entities.refact.IDBAnnotationMethod)
	 */
	public IFeatureToDBAnnotation addDBAnnotation(String db, String accession, DBAnnotationMethod method) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeature#getFeatureType()
	 */
	public IFeatureType getFeatureType() {
		return featureType;
	}

}
