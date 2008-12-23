/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

import org.cycads.general.ParametersDefault;

public interface Note<S extends NoteSource< ? >>
{

	public static final String	TYPE_FUCNTION	= ParametersDefault.annotationNoteTypeFunction();

	public String getType();

	public String getValue();

	public S getHolder();

}