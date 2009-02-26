/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.HasSynonyms;

public interface Annotation<T extends Type, M extends AnnotationMethod> extends Noteble, HasSynonyms
{
	public M getAnnotationMethod();

	public T getType();

}