/*
 * Created on 11/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.SimpleNote;
import org.cycads.entities.sequence.LocationBJ;

public class LocationDBLinkBJ implements DBLink<LocationBJ, DBRecordBJ, AnnotationMethodBJ>
{

	LocationBJ			source;
	DBRecordBJ			target;
	AnnotationMethodBJ	method;

	protected LocationDBLinkBJ(LocationBJ source, DBRecordBJ target, AnnotationMethodBJ method) {
		this.source = source;
		this.target = target;
		this.method = method;
	}

	@Override
	public DBRecordBJ getDBRecordTarget() {
		return target;
	}

	@Override
	public AnnotationMethodBJ getAnnotationMethod() {
		return method;
	}

	@Override
	public LocationBJ getSource() {
		return source;
	}

	@Override
	public Note<LocationDBLinkBJ> createNote(String value, String noteTypeName) {
		return addNote(new SimpleNote<LocationDBLinkBJ>(this, value, noteTypeName));
	}

	@Override
	public Note<LocationDBLinkBJ> addNote(Note<LocationDBLinkBJ> note) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note<LocationDBLinkBJ> getNote(String value, String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note<LocationDBLinkBJ>> getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note<LocationDBLinkBJ>> getNotes(String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChangeListener(ChangeListener<Note<LocationDBLinkBJ>> cl, ChangeType ct) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<LocationDBLinkBJ>> cl, ChangeType ct) {
		// TODO Auto-generated method stub

	}

}
