/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence.feature;

import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationTarget;
import org.cycads.entities.annotation.DBAnnotationMethod;
import org.cycads.entities.sequence.Location;
import org.cycads.entities.sequence.Sequence;

public interface Feature extends AnnotationTarget, Annotation<Sequence, Feature>
{

	public String getName();

	public FeatureType getFeatureType();

	public Sequence getSequence();

	public Location getLocation();

	public FeatureAnnotationMethod getMethod();

	// DBAnnotations methods
	public Collection<FeatureToDBAnnotation> getdBAnnotations();

	public void addDBAnnotation(FeatureToDBAnnotation featureToDBAnnotation);

	// public FeatureToDBAnnotation addDBAnnotation(ExternalDatabase db, String accession, DBAnnotationMethod method);
	//
	public FeatureToDBAnnotation addDBAnnotation(String db, String accession, DBAnnotationMethod method);

}