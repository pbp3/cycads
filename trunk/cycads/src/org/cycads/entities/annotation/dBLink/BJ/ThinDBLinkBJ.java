/*
 * Created on 04/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.Collection;

import org.biojavax.CrossRef;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.annotation.dBLink.DBLinkSource;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.exceptions.InvalidMethod;

// Without AnnotationMethod and notes
public class ThinDBLinkBJ<S extends DBLinkSource< ? , ? , ? , ? >> implements DBLink<S, DBRecordBJ, AnnotationMethodBJ>
{
	S			source;
	DBRecordBJ	target;

	public ThinDBLinkBJ(S source, DBRecordBJ target)
	{
		this.source = source;
		this.target = target;
	}

	public ThinDBLinkBJ(S source, CrossRef crossRef)
	{
		this.source = source;
		this.target = new DBRecordBJ(crossRef);
	}

	@Override
	public DBRecordBJ getDBRecordTarget()
	{
		return target;
	}

	@Override
	public AnnotationMethodBJ getAnnotationMethod()
	{
		return null;
	}

	@Override
	public S getSource()
	{
		return source;
	}

	@Override
	public Note< ? extends NoteSource> createNote(String value, String noteTypeName)
	{
		return null;
	}

	@Override
	public Note< ? extends Annotation<S, AnnotationMethodBJ>> addNote(
			Note< ? extends Annotation<S, AnnotationMethodBJ>> note)
	{
		throw new InvalidMethod();
	}

	@Override
	public Note< ? extends Annotation<S, AnnotationMethodBJ>> getNote(String value, String noteTypeName)
	{
		throw new InvalidMethod();
	}

	@Override
	public Collection<Note< ? extends Annotation<S, AnnotationMethodBJ>>> getNotes()
	{
		return null;
	}

	@Override
	public Collection<Note< ? extends Annotation<S, AnnotationMethodBJ>>> getNotes(String noteTypeName)
	{
		return null;
	}

	@Override
	public void addChangeListener(ChangeListener<Note< ? extends Annotation<S, AnnotationMethodBJ>>> cl, ChangeType ct)
	{
		throw new InvalidMethod();
	}

	@Override
	public boolean isUnchanging(ChangeType ct)
	{
		throw new InvalidMethod();
	}

	@Override
	public void removeChangeListener(ChangeListener<Note< ? extends Annotation<S, AnnotationMethodBJ>>> cl,
			ChangeType ct)
	{
		throw new InvalidMethod();
	}

	@Override
	public String toString()
	{
		return DBRecordDBRecordLinkBJ.joinTermName(getSource().toString(), "", getDBRecordTarget().toString());
	}

}
