/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym;

import java.sql.Connection;
import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.DbxrefDbxrefAnnotation;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;

public interface Dbxref<A extends DbxrefDbxrefAnnotation< ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends Noteble, HasSynonyms<X>
{
	public String getDbName();

	public String getAccession();

	public Annotation< ? , ? , ? , ? > createDbxrefAnnotation(A parent, T type, M method, X dbxref, Connection con);

	public Collection<A> getDbxrefDbxrefAnnotations(X synonym);

	public Collection<A> getDbxrefDbxrefAnnotations(T type);

	public Collection<A> getDbxrefDbxrefAnnotations(M method);

	public Collection<A> getDbxrefDbxrefAnnotations(M method, T type);

	public Collection<A> getDbxrefDbxrefAnnotations(AnnotationFilter<A> filter);
}
