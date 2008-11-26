/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

public interface ExternalAnnotationSource extends AnnotationSource<ExternalAnnotation>
{
	public ExternalAnnotation getOrCreateAnnotation(AnnotationMethod method, DBRecord record);

	public ExternalAnnotation getOrCreateAnnotation(AnnotationMethod method, String accession, String dbName);

}
