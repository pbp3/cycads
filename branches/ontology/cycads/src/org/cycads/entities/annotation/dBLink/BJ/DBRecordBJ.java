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
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.dBLink.ExternalDatabase;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.exceptions.MethodNotImplemented;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.BioSql;

public class DBRecordBJ implements DBRecord
{
	CrossRef	crossRef;

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
	public ExternalDatabase<DBRecordBJ> getDatabase() {
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

	@Override
	public OntologyOntologyAnnotBJ createDBLink(AnnotationMethodBJ method, DBRecordBJ record) {
		return new OntologyOntologyAnnotBJ(this, method, record);
	}

	@Override
	public OntologyOntologyAnnotBJ createDBLink(AnnotationMethodBJ method, String accession, String dbName) {
		return new OntologyOntologyAnnotBJ(this, method, getOrCreateDBRecordBJ(dbName, accession));
	}

	@Override
	public void addDBLinkAnnot(OntologyOntologyAnnotBJ link) {
		// Do nothing. The DBLink collection is handled by DBRecordBJ source term.
	}

	protected OntologyOntologyAnnotBJ getLink(String methodName, String target) {
		ComparableTerm term;
		try {
			term = (ComparableTerm) OntologyOntologyAnnotBJ.ontDBRecordDBRecordLink.getTerm(OntologyOntologyAnnotBJ.joinTermName(
				this.toString(), methodName, target));
		}
		catch (NoSuchElementException exception) {
			try {
				term = (ComparableTerm) OntologyOntologyAnnotBJ.ontDBRecordDBRecordLink.getTerm(OntologyOntologyAnnotBJ.joinTermName(
					target, methodName, this.toString()));
			}
			catch (NoSuchElementException ex) {
				return null;
			}
		}
		return new OntologyOntologyAnnotBJ(term);
	}

	@Override
	public OntologyOntologyAnnotBJ getDBLinkAnnot(DBRecordBJ source, AnnotationMethodBJ method, DBRecordBJ target) {
		if (source != this) {
			return null;
		}
		return getLink(method.getName(), target.toString());
	}

	@Override
	public Collection<OntologyOntologyAnnotBJ> getDBLinkAnnots(AnnotationMethodBJ method, DBRecordBJ target) {
		ArrayList<OntologyOntologyAnnotBJ> ret = new ArrayList<OntologyOntologyAnnotBJ>();
		OntologyOntologyAnnotBJ link = getLink(method.getName(), target.toString());
		if (link != null) {
			ret.add(link);
		}
		return ret;
	}

	@Override
	public Collection<OntologyOntologyAnnotBJ> getDBLinkAnnots(AnnotationMethodBJ method, String dbName, String accession) {
		ArrayList<OntologyOntologyAnnotBJ> ret = new ArrayList<OntologyOntologyAnnotBJ>();
		OntologyOntologyAnnotBJ link = getLink(method.getName(), DBRecordBJ.joinDBNameAndAccession(dbName, accession));
		if (link != null) {
			ret.add(link);
		}
		return ret;
	}

	@Override
	public Collection<OntologyOntologyAnnotBJ> getDBLinkAnnots(DBLinkAnnotFilter<OntologyOntologyAnnotBJ> filter) {
		Collection<ComparableTerm> terms = BioSql.getTermsWithCrossRef(this.getCrossRef());
		Collection<OntologyOntologyAnnotBJ> result = new ArrayList<OntologyOntologyAnnotBJ>(terms.size());
		for (ComparableTerm term : terms) {
			OntologyOntologyAnnotBJ link = new OntologyOntologyAnnotBJ(term);
			if (filter.accept(link)) {
				result.add(link);
			}
		}
		return result;
	}

	@Override
	public Note<DBRecordBJ> createNote(String value, String noteTypeName) {
		throw new MethodNotImplemented();
	}

	@Override
	public Note<DBRecordBJ> createNote(Note< ? > note) {
		throw new MethodNotImplemented();
	}

	@Override
	public Note<DBRecordBJ> addNote(Note< ? > note) {
		throw new MethodNotImplemented();
	}

	@Override
	public Note<DBRecordBJ> getNote(String value, String noteTypeName) {
		throw new MethodNotImplemented();
	}

	@Override
	public Collection<Note<DBRecordBJ>> getNotes() {
		throw new MethodNotImplemented();
	}

	@Override
	public Collection<Note<DBRecordBJ>> getNotes(String noteTypeName) {
		throw new MethodNotImplemented();
	}

	@Override
	public void addChangeListener(ChangeListener<Note<DBRecordBJ>> cl, ChangeType ct) {
		throw new MethodNotImplemented();
	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		throw new MethodNotImplemented();
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<DBRecordBJ>> cl, ChangeType ct) {
		throw new MethodNotImplemented();
	}

	@Override
	public OntologyOntologyAnnotBJ createDBLink(String method, DBRecordBJ target) {
		return createDBLink(AnnotationMethodBJ.getInstance(method), target);
	}

	@Override
	public OntologyOntologyAnnotBJ createDBLink(String method, String accession, String dbName) {
		return createDBLink(AnnotationMethodBJ.getInstance(method), accession, dbName);
	}

	@Override
	public OntologyOntologyAnnotBJ getDBLinkAnnot(DBRecordBJ source, String method, DBRecordBJ target) {
		return getDBLinkAnnot(source, AnnotationMethodBJ.getInstance(method), target);
	}

	@Override
	public Collection<OntologyOntologyAnnotBJ> getDBLinkAnnots(String method, DBRecordBJ target) {
		return getDBLinkAnnots(AnnotationMethodBJ.getInstance(method), target);
	}

	@Override
	public Collection<OntologyOntologyAnnotBJ> getDBLinkAnnots(String method, String dbName, String accession) {
		return getDBLinkAnnots(AnnotationMethodBJ.getInstance(method), dbName, accession);
	}

	@Override
	public Note<DBRecordBJ> addNote(String value, String type) {
		return addNote(createNote(value, type));
	}

}
