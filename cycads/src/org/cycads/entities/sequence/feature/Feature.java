/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence.feature;

import java.util.Collection;

import org.cycads.entities.NoteType;
import org.cycads.entities.annotation.DBAnnotationMethod;
import org.cycads.entities.sequence.Sequence;

public interface Feature
{

	public String getName();

	public FeatureType getFeatureType();

	public Sequence getSequence();

	public Location getLoaction();

	public FeatureAnnotationMethod getMethod();

	//Notes methods
	public Collection<FeatureNote> getNotes();

	public Collection<FeatureNote> getNotes(NoteType noteType);

	public Collection<FeatureNote> getNotes(String noteTypeName);

	public void addNote(FeatureNote note);

	public FeatureNote addNote(String value, String noteType);

	//DBAnnotations methods
	public Collection<FeatureToDBAnnotation> getdBAnnotations();

	public void addDBAnnotation(FeatureToDBAnnotation featureToDBAnnotation);

	//	public FeatureToDBAnnotation addDBAnnotation(ExternalDatabase db, String accession, DBAnnotationMethod method);
	//
	public FeatureToDBAnnotation addDBAnnotation(String db, String accession, DBAnnotationMethod method);

}