/*
 * Created on 02/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.biojavax.CrossRef;
import org.biojavax.RichObjectFactory;
import org.biojavax.SimpleCrossRef;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLinkFilter;
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.dBLink.ExternalDatabase;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.exceptions.MethodNotImplemented;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;

public class DBRecordBJ implements DBRecord<DBRecordDBRecordLinkBJ, DBRecordBJ, DBRecordBJ, AnnotationMethodBJ>
{
	CrossRef			crossRef;
	ExternalDatabaseBJ	database;

	protected DBRecordBJ(ExternalDatabaseBJ database, String accession)
	{
		this.database = database;
		crossRef = (CrossRef) RichObjectFactory.getObject(SimpleCrossRef.class, new Object[] {database.getDbName(),
			accession, 0});
	}

	@Override
	public String getAccession()
	{
		return getCrossRef().getAccession();
	}

	@Override
	public String getDatabaseName()
	{
		return getDatabase().getDbName();
	}

	@Override
	public ExternalDatabase<DBRecordBJ> getDatabase()
	{
		return database;
	}

	public CrossRef getCrossRef()
	{
		return crossRef;
	}

	protected static DBRecordBJ getOrCreateDBRecordBJ(String dbName, String accession)
	{
		return ExternalDatabaseBJ.getOrCreateExternalDB(dbName).getOrCreateDBRecord(accession);
	}

	public static String joinDBNameAndAccession(String dbName, String accession)
	{
		return dbName + ParametersDefault.DBRecordSeparator() + accession;
	}

	public static String[] splitDBNameAndAccession(String dbNameAndAccession)
	{
		String[] a = dbNameAndAccession.split(ParametersDefault.DBRecordSeparator());
		if (a.length != 2 || a[0].length() == 0 || a[1].length() == 0)
		{
			throw new IllegalArgumentException(Messages.ExceptionDBRecordBJCreateDBNameAccession());
		}
		return a;
	}

	@Override
	public String toString()
	{
		return joinDBNameAndAccession(getDatabaseName(), getAccession());
	}

	@Override
	public DBRecordDBRecordLinkBJ createDBLink(AnnotationMethodBJ method, DBRecordBJ record)
	{
		return new DBRecordDBRecordLinkBJ(this, method, record);
	}

	@Override
	public DBRecordDBRecordLinkBJ createDBLink(AnnotationMethodBJ method, String accession, String dbName)
	{
		return new DBRecordDBRecordLinkBJ(this, method, getOrCreateDBRecordBJ(dbName, accession));
	}

	@Override
	public void addDBLink(DBRecordDBRecordLinkBJ link)
	{
		// Do nothing. The DBLink collection is handled by DBRecordBJ source term.
	}

	protected DBRecordDBRecordLinkBJ getLink(String methodName, String target)
	{
		ComparableTerm term;
		try
		{
			term = (ComparableTerm) DBRecordDBRecordLinkBJ.ontDBRecordDBRecordLink.getTerm(DBRecordDBRecordLinkBJ.joinTermName(
				this.toString(), methodName, target));
		}
		catch (NoSuchElementException exception)
		{
			try
			{
				term = (ComparableTerm) DBRecordDBRecordLinkBJ.ontDBRecordDBRecordLink.getTerm(DBRecordDBRecordLinkBJ.joinTermName(
					target, methodName, this.toString()));
			}
			catch (NoSuchElementException ex)
			{
				return null;
			}
		}
		return new DBRecordDBRecordLinkBJ(term);
	}

	@Override
	public DBRecordDBRecordLinkBJ getDBLink(DBRecordBJ source, AnnotationMethodBJ method, DBRecordBJ target)
	{
		if (source != this)
		{
			return null;
		}
		return getLink(method.getName(), target.toString());
	}

	@Override
	public Collection<DBRecordDBRecordLinkBJ> getDBLinks(AnnotationMethodBJ method, DBRecordBJ target)
	{
		ArrayList<DBRecordDBRecordLinkBJ> ret = new ArrayList<DBRecordDBRecordLinkBJ>();
		DBRecordDBRecordLinkBJ link = getLink(method.getName(), target.toString());
		if (link != null)
		{
			ret.add(link);
		}
		return ret;
	}

	@Override
	public Collection<DBRecordDBRecordLinkBJ> getDBLinks(AnnotationMethodBJ method, String dbName, String accession)
	{
		ArrayList<DBRecordDBRecordLinkBJ> ret = new ArrayList<DBRecordDBRecordLinkBJ>();
		DBRecordDBRecordLinkBJ link = getLink(method.getName(), DBRecordBJ.joinDBNameAndAccession(dbName, accession));
		if (link != null)
		{
			ret.add(link);
		}
		return ret;
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
		throw new MethodNotImplemented();
	}

	@Override
	public Note<DBRecordBJ> addNote(Note<DBRecordBJ> note)
	{
		throw new MethodNotImplemented();
	}

	@Override
	public Note<DBRecordBJ> getNote(String value, String noteTypeName)
	{
		throw new MethodNotImplemented();
	}

	@Override
	public Collection<Note<DBRecordBJ>> getNotes()
	{
		throw new MethodNotImplemented();
	}

	@Override
	public Collection<Note<DBRecordBJ>> getNotes(String noteTypeName)
	{
		throw new MethodNotImplemented();
	}

	@Override
	public void addChangeListener(ChangeListener<Note<DBRecordBJ>> cl, ChangeType ct)
	{
		throw new MethodNotImplemented();
	}

	@Override
	public boolean isUnchanging(ChangeType ct)
	{
		throw new MethodNotImplemented();
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<DBRecordBJ>> cl, ChangeType ct)
	{
		throw new MethodNotImplemented();
	}

}
