/*
 * Created on 02/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.Collection;

import org.biojavax.CrossRef;
import org.biojavax.RichObjectFactory;
import org.biojavax.SimpleCrossRef;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLinkFilter;
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.dBLink.ExternalDatabase;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;

public class DBRecordBJ implements DBRecord<DBRecordDBRecordLinkBJ, DBRecordBJ, DBRecordBJ, AnnotationMethodBJ>
{
	CrossRef			crossRef;
	ExternalDatabaseBJ	database;

	protected DBRecordBJ(ExternalDatabaseBJ database, String accession) {
		this.database = database;
		crossRef = (CrossRef) RichObjectFactory.getObject(SimpleCrossRef.class, new Object[] {database.getDbName(),
			accession, 0});
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
	public ExternalDatabase<DBRecordBJ> getDatabase() {
		return database;
	}

	public CrossRef getCrossRef() {
		return crossRef;
	}

	protected static DBRecordBJ getOrCreateDBRecordBJ(String dbName, String accession) {
		return ExternalDatabaseBJ.getOrCreateExternalDB(dbName).getOrCreateDBRecord(accession);
	}

	@Override
	public DBRecordDBRecordLinkBJ createDBLink(AnnotationMethodBJ method, DBRecordBJ record) {
		return new DBRecordDBRecordLinkBJ(this, method, record);
	}

	@Override
	public DBRecordDBRecordLinkBJ createDBLink(AnnotationMethodBJ method, String accession, String dbName) {
		return new DBRecordDBRecordLinkBJ(this, method, getOrCreateDBRecordBJ(dbName, accession));
	}

	@Override
	public void addDBLink(DBRecordDBRecordLinkBJ link) {
		//Do nothing. The DBLink collection is handled by DBRecordBJ source term.
	}

	@Override
	public DBRecordDBRecordLinkBJ getDBLink(AnnotationMethodBJ method, DBRecordBJ record, DBRecordBJ source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DBRecordDBRecordLinkBJ> getDBLinks(AnnotationMethodBJ method, DBRecordBJ record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DBRecordDBRecordLinkBJ> getDBLinks(AnnotationMethodBJ method, String accession, String dbName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DBRecordDBRecordLinkBJ> getDBLinks(DBLinkFilter<DBRecordDBRecordLinkBJ> filter) {
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
