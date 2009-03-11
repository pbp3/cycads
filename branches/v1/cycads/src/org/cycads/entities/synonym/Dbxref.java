/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.DbxrefDbxrefAnnotation;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;

public interface Dbxref<A extends DbxrefDbxrefAnnotation< ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends Noteble, HasSynonyms<X>, AnnotationFinder<A, X, T, M>
{
	public String getDbName();

	public String getAccession();

	public A createDbxrefAnnotation(M method, X dbxref);

}
