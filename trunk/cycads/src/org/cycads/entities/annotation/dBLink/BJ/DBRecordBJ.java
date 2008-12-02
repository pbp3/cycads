/*
 * Created on 02/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.Collection;

import org.biojavax.ontology.ComparableOntology;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.annotation.dBLink.DBLinkFilter;
import org.cycads.entities.annotation.dBLink.DBLinkSource;
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.dBLink.ExternalDatabase;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.entities.note.NotesContainer;

public class DBRecordBJ implements DBRecord<DBRecordBJ>
{
	ComparableTerm	term;

	public DBRecordBJ(ComparableTerm term) {
		this.term = term;
	}

	@Override
	public String getAccession() {
		return term.getName();
	}

	@Override
	public ExternalDatabase getDatabase() {
		return new ExternalDatabaseBJ((ComparableOntology) term.getOntology());
	}

	@Override
	public String getDatabaseName() {
		return term.getOntology().getName();
	}

	public ComparableTerm getTerm() {
		return term;
	}

	@Override
	public <R extends DBRecord<R>> DBLink<DBRecordBJ, R> createDBLink(AnnotationMethod method, R record,
			NotesContainer<Note<DBLink<DBRecordBJ, R>>> notes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <R extends DBRecord<R>> DBLink<DBRecordBJ, R> createDBLink(AnnotationMethod method, String accession,
			String dbName, NotesContainer<Note<DBLink<DBRecordBJ, R>>> notes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends DBLinkSource<S>> void addDBLink(DBLink<S, ? extends DBRecord< ? >> link) {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends DBLinkSource<S>, R extends DBRecord<R>> DBLink<S, R> getDBLink(AnnotationMethod method, R record,
			S source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends DBLinkSource<S>, R extends DBRecord<R>> Collection<DBLink<S, R>> getDBLinks(
			AnnotationMethod method, R record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends DBLinkSource<S>> Collection<DBLink<S, ? extends DBRecord< ? >>> getDBLinks(
			AnnotationMethod method, String accession, String dbName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends DBLinkSource<S>> Collection<DBLink<S, ? extends DBRecord< ? >>> getDBLinks(DBLinkFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note< ? extends NoteSource> createNote(String value, String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note<DBRecordBJ> addNote(Note<DBRecordBJ> note) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note<DBRecordBJ> getNote(String value, String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note<DBRecordBJ>> getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note<DBRecordBJ>> getNotes(String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChangeListener(ChangeListener<Note<DBRecordBJ>> cl, ChangeType ct) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<DBRecordBJ>> cl, ChangeType ct) {
		// TODO Auto-generated method stub

	}

}
