/*
 * Created on 02/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;

public class DBRecordDBRecordLinkBJ implements DBLink<DBRecordBJ, DBRecordBJ>
{

	@Override
	public DBRecordBJ getDBRecordTarget()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnnotationMethod getAnnotationMethod()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBRecordBJ getSource()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note< ? extends NoteSource> createNote(String value, String noteTypeName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note< ? extends Annotation<DBRecordBJ>> addNote(Note< ? extends Annotation<DBRecordBJ>> note)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note< ? extends Annotation<DBRecordBJ>> getNote(String value, String noteTypeName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note< ? extends Annotation<DBRecordBJ>>> getNotes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note< ? extends Annotation<DBRecordBJ>>> getNotes(String noteTypeName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChangeListener(ChangeListener<Note< ? extends Annotation<DBRecordBJ>>> cl, ChangeType ct)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUnchanging(ChangeType ct)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeChangeListener(ChangeListener<Note< ? extends Annotation<DBRecordBJ>>> cl, ChangeType ct)
	{
		// TODO Auto-generated method stub

	}

}
