/*
 * Created on 04/03/2009
 */
package org.cycads.entities.annotation.SQL;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.note.TypeSQL;
import org.cycads.entities.synonym.DbxrefSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public abstract class AnnotationSQL<AParent extends Annotation< ? , ? , ? , ? >> extends HasSynonymsNotebleSQL
		implements Annotation<AParent, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{

	@Override
	public AnnotationMethodSQL getAnnotationMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AParent getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeSQL getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
