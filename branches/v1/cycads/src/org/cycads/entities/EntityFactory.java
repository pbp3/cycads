/*
 * Created on 26/02/2009
 */
package org.cycads.entities;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface EntityFactory<D extends Dbxref< ? , ? , ? , ? >, M extends AnnotationMethod, T extends Type>
{
	public D getDbxref(String dbName, String accession);

	public M getAnnotationMethod(String name);

	public T getNoteType(String name);

	public T getAnnotationType(String name);

}
