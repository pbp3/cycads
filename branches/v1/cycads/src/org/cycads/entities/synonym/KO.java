/*
 * Created on 10/03/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.DbxrefDbxrefAnnotation;

public interface KO<X extends Dbxref< ? >, A extends DbxrefDbxrefAnnotation< ? , ? , ? , ? >, M extends AnnotationMethod>
		extends Dbxref<X>
{
	public final static String	DBNAME	= "KO";

	public String getDefinition();

	public void setDefinition(String definition);

	public String getName();

	public void setName(String name);

	public A addEcAnnotation(M method, String ec);

}
