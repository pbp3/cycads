/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

public interface ExternalAnnotation extends Annotation<ExternalAnnotation>, ExternalAnnotationSource
{

	public DBRecord getDBRecord();

}