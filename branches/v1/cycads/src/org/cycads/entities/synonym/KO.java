/*
 * Created on 10/03/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.DbxrefDbxrefAnnotation;
import org.cycads.entities.note.Type;

public interface KO<A extends DbxrefDbxrefAnnotation< ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends Dbxref<A, X, T, M>
{
	public final static String	DBNAME	= "KO";

	public String getDefinition();

	public void setDefinition(String definition);

	public String getName();

	public void setName(String name);

	public A addEcAnnotation(M method, String ecNumber);

}
