/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Note;

public interface FeatureSource
{
	public Feature createFeature(AnnotationMethod method, String type, Note<Feature> notes);

	public CDS createCDS(AnnotationMethod method);

	public RNA createRNA(AnnotationMethod method, String type);

	public Gene createGene(AnnotationMethod method);
}
