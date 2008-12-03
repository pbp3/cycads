/*
 * Created on 02/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.Collection;

import org.biojavax.ontology.ComparableOntology;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.dBLink.DBLinkFilter;
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.dBLink.ExternalDatabase;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;

public class DBRecordBJ implements DBRecord<DBRecordDBRecordLinkBJ, DBRecordBJ, DBRecordBJ>
{
	ComparableTerm	term;

	public DBRecordBJ(ComparableTerm term)
	{
		this.term = term;
	}

	@Override
	public String getAccession()
	{
		return term.getName();
	}

	@Override
	public String getDatabaseName()
	{
		return term.getOntology().getName();
	}

	@Override
	public ExternalDatabase<DBRecordBJ> getDatabase()
	{
		return ExternalDatabaseBJ.getOrCreateInstance((ComparableOntology) term.getOntology());
	}

	public ComparableTerm getTerm()
	{
		return term;
	}

	@Override
	public DBRecordDBRecordLinkBJ createDBLink(AnnotationMethod method, DBRecordBJ record)
	{
		return new DBRecordDBRecordLinkBJ(this, method, record);
	}

	@Override
	public DBRecordDBRecordLinkBJ createDBLink(AnnotationMethod method, String accession, String dbName)
	{
		return new DBRecordDBRecordLinkBJ(this, method, record);
	}

	@Override
	public void addDBLink(DBRecordDBRecordLinkBJ link)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public DBRecordDBRecordLinkBJ getDBLink(AnnotationMethod method, DBRecordBJ record, DBRecordBJ source)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DBRecordDBRecordLinkBJ> getDBLinks(AnnotationMethod method, DBRecordBJ record)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DBRecordDBRecordLinkBJ> getDBLinks(AnnotationMethod method, String accession, String dbName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DBRecordDBRecordLinkBJ> getDBLinks(DBLinkFilter<DBRecordDBRecordLinkBJ> filter)
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
	public Note<DBRecordBJ> addNote(Note<DBRecordBJ> note)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note<DBRecordBJ> getNote(String value, String noteTypeName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note<DBRecordBJ>> getNotes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note<DBRecordBJ>> getNotes(String noteTypeName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChangeListener(ChangeListener<Note<DBRecordBJ>> cl, ChangeType ct)
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
	public void removeChangeListener(ChangeListener<Note<DBRecordBJ>> cl, ChangeType ct)
	{
		// TODO Auto-generated method stub

	}

}
