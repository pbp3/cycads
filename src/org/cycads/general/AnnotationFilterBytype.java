/*
 * Created on 24/03/2010
 */
package org.cycads.general;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.note.Type;

public class AnnotationFilterBytype implements AnnotationFilter
{
	Collection<Type>	types;

	public AnnotationFilterBytype(Collection<Type> types) {
		this.types = types;
	}

	public AnnotationFilterBytype(Type type) {
		this.types = new ArrayList<Type>(1);
		types.add(type);
	}

	@Override
	public boolean isValid(Annotation annot) {
		Collection<Type> annotTypes = annot.getTypes();
		for (Type type : types) {
			if (annotTypes.contains(type)) {
				return true;
			}
		}
		return false;
	}

}
