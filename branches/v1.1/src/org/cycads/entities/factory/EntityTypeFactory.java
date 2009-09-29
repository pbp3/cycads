/*
 * Created on 28/09/2009
 */
package org.cycads.entities.factory;

import org.cycads.entities.note.Type;

public interface EntityTypeFactory<T extends Type>
{
	public T getNoteType(String name);

	public T getAnnotationType(String name);

	public T getAnnotationTypeCDS();

	public T getAnnotationTypeGene();

}
