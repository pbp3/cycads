/*
 * Created on 26/02/2009
 */
package org.cycads.entities;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface EntityFactory
{
	public Dbxref getDbxref(String dbName, String accession);

	public AnnotationMethod getAnnotationMethod(String name);

	public Type getNoteType(String name);

	public Type getAnnotationType(String name);

}
