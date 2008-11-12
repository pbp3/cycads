/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

import java.util.Collection;

public interface Feature
{

	public Sequence getSequence();

	public Location getLoaction();

	public FeatureAnnotationMethod getMethod();

	public Collection<FeatureNote> getNotes();

	public void addNote(FeatureNote note);

	public FeatureNote addNote(String value, String noteType);

	public String getName();

	public Collection<FeatureToDBAnnotation> getdBAnnotations();

	public void addDBAnnotation(FeatureToDBAnnotation featureToDBAnnotation);

	public FeatureToDBAnnotation addDBAnnotation(ExternalDatabase db, String accession, DBAnnotationMethod method);

	public FeatureToDBAnnotation addDBAnnotation(String db, String accession, DBAnnotationMethod method);

	public FeatureType getFeatureType();

}