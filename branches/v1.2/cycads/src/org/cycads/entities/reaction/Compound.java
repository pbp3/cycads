/*
 * Created on 10/07/2009
 */
package org.cycads.entities.reaction;

import org.cycads.entities.annotation.AnnotationObject;
import org.cycads.entities.annotation.AnnotationObjectType;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public interface Compound<X extends Dbxref< ? , ? , ? , ? >, AOT extends AnnotationObjectType>
		extends Noteble, HasSynonyms<X>, AnnotationObject<AOT>
{
	public boolean isSmallMolecule();
}
