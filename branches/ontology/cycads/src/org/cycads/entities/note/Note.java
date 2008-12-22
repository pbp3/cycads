/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

public interface Note<S extends NoteSource< ? >>
{

	public String getType();

	public String getValue();

	public S getHolder();

}