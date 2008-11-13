/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

public interface DBRecord extends AnnotationSource, AnnotationTarget
{
	public String getAccession();

	public ExternalDatabase getDatabase();

}