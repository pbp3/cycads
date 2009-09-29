/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public interface Annotation<Source, Target, AParent extends Annotation< ? , ? , ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends Noteble, HasSynonyms<X>
{
	public M getAnnotationMethod();

	public Collection<T> getTypes();

	public boolean hasType(String type);

	public T addType(String type);

	public T addType(T type);

	public Collection<AParent> getParents();

	public void addParent(AParent parent);

	public void setScore(String score);

	public String getScore();
}