/*
 * Created on 26/02/2009
 */
package org.cycads.entities;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.Function;
import org.cycads.entities.synonym.KO;

public interface EntityFactory<X extends Dbxref< ? , ? , ? , ? >, M extends AnnotationMethod, T extends Type, O extends Organism< ? , ? , ? , ? , ? , ? >>
{
	public X getDbxref(String dbName, String accession);

	public M getAnnotationMethod(String name);

	public T getNoteType(String name);

	public T getAnnotationType(String name);

	public O getOrganism(int orgId);

	public O createOrganism(int orgId, String name);

	public Function getFunction(String name, String description);

	public KO< ? , ? , ? , ? > getKO(String ko);
}
