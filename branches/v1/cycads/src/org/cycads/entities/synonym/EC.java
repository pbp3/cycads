/*
 * Created on 10/03/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.DbxrefDbxrefAnnotation;
import org.cycads.entities.note.Type;

public interface EC<A extends DbxrefDbxrefAnnotation< ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends Dbxref<A, X, T, M>
{
	public final static String	DBNAME	= "EC";

}
