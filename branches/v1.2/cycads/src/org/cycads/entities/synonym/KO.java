/*
 * Created on 10/03/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;

public interface KO extends Dbxref
{
	public final static String	DBNAME	= "KO";

	public String getDefinition();

	public void setDefinition(String definition);

	public String getName();

	public void setName(String name);

	public Annotation< ? extends KO, ? extends EC> addEcAnnotation(AnnotationMethod method, String ecNumber);

}
