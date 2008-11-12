/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

import java.util.Collection;

public interface Feature
{

	public ISequence getSequence();

	public ILocation getLoaction();

	public IFeatureAnnotationMethod getMethod();

	public Collection<IFeatureNote> getNotes();

	public void addNote(IFeatureNote note);

	public IFeatureNote addNote(String value, String noteType);

	public String getName();

	public Collection<IFeatureToDBAnnotation> getdBAnnotations();

	public void addDBAnnotation(IFeatureToDBAnnotation featureToDBAnnotation);

	public IFeatureToDBAnnotation addDBAnnotation(ExternalDatabase db, String accession, DBAnnotationMethod method);

	public IFeatureToDBAnnotation addDBAnnotation(String db, String accession, DBAnnotationMethod method);

	public IFeatureType getFeatureType();

}