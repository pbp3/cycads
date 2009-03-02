/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public interface Annotation<AParent extends Annotation< ? , ? , ? , ? >, X extends Dbxref< ? >, T extends Type, M extends AnnotationMethod>
		extends Noteble, HasSynonyms<X>
{
	public M getAnnotationMethod();

	public T getType();

	public AParent getParent();
}