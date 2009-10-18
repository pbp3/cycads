/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public interface Annotation<Source, Target, AParent extends Annotation< ? , ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, M extends AnnotationMethod>
		extends Noteble, HasSynonyms<X>, AnnotationObject
{
	public M getAnnotationMethod();

	public Collection<Type> getTypes();

	public boolean hasType(String type);

	public Type addType(String type);

	public Type addType(Type type);

	public Collection<AParent> getParents();

	public void addParent(AParent parent);

	public void setScore(String score);

	public String getScore();

	public Source getSource();

	public Target getTarget();
}