/*
 * Created on 04/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.Collection;

import org.biojavax.CrossRef;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLinkAnnotation;
import org.cycads.entities.annotation.dBLink.DBLinkAnnotationSource;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.exceptions.InvalidMethod;

// Without AnnotationMethod and notes. It has just source and target.
public class ThinDBLinkBJ<S extends DBLinkAnnotationSource< ? , ? , ? >> implements
		DBLinkAnnotation<ThinDBLinkBJ<S>, S, DBRecordBJ, AnnotationMethodBJ>
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
	public Note<ThinDBLinkBJ<S>> createNote(String value, String noteTypeName)
	{
		return null;
	}

	@Override
	public Note<ThinDBLinkBJ<S>> createNote(Note< ? > note)
	{
		return createNote(note.getValue(), note.getType());
	}

	@Override
	public Note<ThinDBLinkBJ<S>> addNote(Note< ? > note)
	{
		throw new InvalidMethod();
	}

	@Override
	public Note<ThinDBLinkBJ<S>> getNote(String value, String noteTypeName)
	{
		throw null;
	}

	@Override
	public Collection<Note<ThinDBLinkBJ<S>>> getNotes()
	{
		return null;
	}

	@Override
	public Collection<Note<ThinDBLinkBJ<S>>> getNotes(String noteTypeName)
	{
		return null;
	}

	@Override
	public void addChangeListener(ChangeListener<Note<ThinDBLinkBJ<S>>> cl, ChangeType ct)
	{
		throw new InvalidMethod();
	}

	@Override
	public boolean isUnchanging(ChangeType ct)
	{
		throw new InvalidMethod();
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<ThinDBLinkBJ<S>>> cl, ChangeType ct)
	{
		throw new InvalidMethod();
	}

	@Override
	public String toString()
	{
		return DBRecordDBRecordLinkBJ.joinTermName(getSource().toString(), "", getDBRecordTarget().toString());
	}

	@Override
	public Note<ThinDBLinkBJ<S>> addNote(String value, String type)
	{
		return addNote(createNote(value, type));
	}

}
