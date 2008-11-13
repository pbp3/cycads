/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.NoteHolder;

public interface DBAnnotation extends NoteHolder<DBAnnotation>
{

	public DBAnnotationMethod getDBAnnotationMethod();

	public DBRecord getDBRecord();

}