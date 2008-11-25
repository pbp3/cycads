/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

public interface ExternalAnnotationSource extends AnnotationSource
{
	public Annotation getOrCreateAnnotation(AnnotationMethod method, DBRecord record);

}
