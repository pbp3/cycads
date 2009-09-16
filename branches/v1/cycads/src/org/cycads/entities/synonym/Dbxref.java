/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.DbxrefAnnotable;
import org.cycads.entities.annotation.DbxrefDbxrefAnnotation;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;
import org.cycads.extract.cyc.CycDBLink;

public interface Dbxref<A extends DbxrefDbxrefAnnotation< ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends Noteble, HasSynonyms<X>, AnnotationFinder<A, X, T, M>, CycDBLink, DbxrefAnnotable<A, X, M>
{
	public String getDbName();

	public String getAccession();

}
