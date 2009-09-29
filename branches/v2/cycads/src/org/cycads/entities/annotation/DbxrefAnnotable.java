/*
 * Created on 16/09/2009
 */
package org.cycads.entities.annotation;

import org.cycads.entities.synonym.Dbxref;

public interface DbxrefAnnotable<A extends DbxrefAnnotation< ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, M extends AnnotationMethod>
{
	public A createDbxrefAnnotation(M method, X dbxref);

	/* Add if Annotation doesn't exist */
	public A addDbxrefAnnotation(M method, X dbxref);

}
