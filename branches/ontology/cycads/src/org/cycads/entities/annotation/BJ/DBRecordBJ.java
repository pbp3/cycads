/*
 * Created on 02/12/2008
 */
package org.cycads.entities.annotation.BJ;

import java.util.Collection;

import org.biojavax.CrossRef;
import org.biojavax.RichAnnotation;
import org.biojavax.RichObjectFactory;
import org.biojavax.SimpleCrossRef;
import org.cycads.entities.annotation.DBRecord;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesHashTable;
import org.cycads.entities.note.NotesToAnnotationBJ;
import org.cycads.entities.note.SimpleNote;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;

public class DBRecordBJ implements DBRecord<ExternalDatabaseBJ>
{
	CrossRef							crossRef;
	NotesHashTable<Note<DBRecord< ? >>>	notes;

	// Created by ExternalDatabaseBJ
	protected DBRecordBJ(ExternalDatabaseBJ database, String accession) {
		crossRef = (CrossRef) RichObjectFactory.getObject(SimpleCrossRef.class, new Object[] {database.getDbName(),
			accession, 0});
	}

	public DBRecordBJ(CrossRef crossRef) {
		this.crossRef = crossRef;
	}

	@Override
	public String getAccession() {
		return getCrossRef().getAccession();
	}

	@Override
	public String getDatabaseName() {
		return getDatabase().getDbName();
	}

	@Override
	public ExternalDatabaseBJ getDatabase() {
		return ExternalDatabaseBJ.getOrCreateExternalDB(crossRef.getDbname());
	}

	public CrossRef getCrossRef() {
		return crossRef;
	}

	public static DBRecordBJ getOrCreateDBRecordBJ(String dbName, String accession) {
		return ExternalDatabaseBJ.getOrCreateExternalDB(dbName).getOrCreateDBRecord(accession);
	}

	public static String joinDBNameAndAccession(String dbName, String accession) {
		return dbName + ParametersDefault.DBRecordSeparator() + accession;
	}

	public static String[] splitDBNameAndAccession(String dbNameAndAccession) {
		String[] a = dbNameAndAccession.split(ParametersDefault.DBRecordSeparator());
		if (a.length != 2 || a[0].length() == 0 || a[1].length() == 0) {
			throw new IllegalArgumentException(Messages.exceptionDBRecordBJSplitDBNameAccession(dbNameAndAccession));
		}
		return a;
	}

	@Override
	public String toString() {
		return joinDBNameAndAccession(getDatabaseName(), getAccession());
	}

	public NotesHashTable<Note<DBRecord< ? >>> getNotesHash() {
		if (notes == null) {
			notes = NotesToAnnotationBJ.createNotesHashTable((RichAnnotation) getCrossRef().getAnnotation(), this);
		}
		return notes;
	}

	@Override
	public Note<DBRecord< ? >> createNote(String type, String value) {
		return addNote(new SimpleNote<DBRecord< ? >>(this, type, value));
	}

	@Override
	public Note<DBRecord< ? >> createNote(Note< ? > note) {
		return createNote(note.getType(), note.getValue());
	}

	@Override
	public Note<DBRecord< ? >> addNote(Note< ? > note) {
		if (note.getHolder() != this) {
			note = createNote(note.getType(), note.getValue());
		}
		return getNotesHash().addNote(note);
	}

	@Override
	public Note<DBRecord< ? >> getNote(String noteTypeName, String value) {
		return getNotesHash().getNote(noteTypeName, value);
	}

	@Override
	public Collection<Note<DBRecord< ? >>> getNotes() {
		return getNotesHash().getNotes();
	}

	@Override
	public Collection<Note<DBRecord< ? >>> getNotes(String noteTypeName) {
		return getNotesHash().getNotes(noteTypeName);
	}

	@Override
	public Note<DBRecord< ? >> addNote(String type, String value) {
		return addNote(createNote(type, value));
	}

	@Override
	public Collection<String> getNotesValues(String noteTypeName) {
		return getNotesHash().getNotesValues(noteTypeName);
	}

	@Override
	public void addChangeListener(ChangeListener<Note<DBRecord< ? >>> cl, ChangeType ct) {
		getNotesHash().addChangeListener(cl, ct);
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<DBRecord< ? >>> cl, ChangeType ct) {
		getNotesHash().removeChangeListener(cl, ct);
	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		return getNotesHash().isUnchanging(ct);
	}

}
