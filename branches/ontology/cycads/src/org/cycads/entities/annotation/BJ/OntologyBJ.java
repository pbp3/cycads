/*
 * Created on 22/12/2008
 */
package org.cycads.entities.annotation.BJ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.biojavax.CrossRef;
import org.biojavax.RichObjectFactory;
import org.biojavax.SimpleCrossRef;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.Ontology;
import org.cycads.entities.note.Note;
import org.cycads.general.biojava.BioSql;

public class OntologyBJ extends DBRecordBJ
		implements Ontology<OntologyOntologyAnnotBJ, OntologyBJ, AnnotationMethodBJ, DBRecordBJ>
{

	// Created by ExternalDatabaseBJ
	protected OntologyBJ(ExternalDatabaseBJ database, String accession) {
		this((CrossRef) RichObjectFactory.getObject(SimpleCrossRef.class, new Object[] {database.getDbName(),
			accession, 0}));
	}

	public OntologyBJ(CrossRef crossRef) {
		super(crossRef);
	}

	public static OntologyBJ getOrCreateOntologyBJ(String dbName, String accession) {
		return ExternalDatabaseBJ.getOrCreateExternalDB(dbName).getOrCreateOntology(accession);
	}

	@Override
	public void addFunction(String function) {
		addNote(Note.TYPE_FUCNTION, function);
	}

	@Override
	public Collection<String> getFunctions() {
		return getNotesValues(Note.TYPE_FUCNTION);
	}

	@Override
	public DBRecordBJ getDBRecord() {
		return this;
	}

	//return the first occurrence
	@Override
	public OntologyOntologyAnnotBJ getOntologyAnnot(AnnotationMethodBJ method, OntologyBJ target) {
		Collection<ComparableTerm> terms = BioSql.getTermsWithCrossRef(this.getCrossRef());
		for (ComparableTerm term : terms) {
			OntologyOntologyAnnotBJ annotation = new OntologyOntologyAnnotBJ(term);
			if (annotation.getAnnotationMethod().equals(method) && annotation.getOntology().equals(target)) {
				return annotation;
			}
		}
		return null;
	}

	@Override
	public OntologyOntologyAnnotBJ getOntologyAnnot(AnnotationMethodBJ method, String accession, String dbName) {
		return getOntologyAnnot(method, getOrCreateOntologyBJ(dbName, accession));
	}

	@Override
	public Collection<OntologyOntologyAnnotBJ> getOntologyAnnots(OntologyBJ target) {
		Collection<ComparableTerm> terms = BioSql.getTermsWithCrossRef(this.getCrossRef());
		Collection<OntologyOntologyAnnotBJ> result = new ArrayList<OntologyOntologyAnnotBJ>(terms.size());
		for (ComparableTerm term : terms) {
			OntologyOntologyAnnotBJ annotation = new OntologyOntologyAnnotBJ(term);
			if (annotation.getOntology().equals(target)) {
				result.add(annotation);
			}
		}
		return result;
	}

	@Override
	public OntologyOntologyAnnotBJ getOrCreateOntologyAnnot(AnnotationMethodBJ method, OntologyBJ target) {
		OntologyOntologyAnnotBJ ret = getOntologyAnnot(method, target);
		if (ret == null) {
			ret = new OntologyOntologyAnnotBJ(OntologyOntologyAnnotBJ.createTermAnnotation(this, method, target));
		}
		return ret;
	}

	@Override
	public OntologyOntologyAnnotBJ getOrCreateOntologyAnnot(AnnotationMethodBJ method, String accession, String dbName) {
		return getOrCreateOntologyAnnot(method, getOrCreateOntologyBJ(dbName, accession));
	}

	@Override
	public Collection<OntologyOntologyAnnotBJ> getOntologyAnnots(String dbName, String accession) {
		return getOntologyAnnots(getOrCreateOntologyBJ(dbName, accession));
	}

	@Override
	public Collection<OntologyOntologyAnnotBJ> getOntologyAnnots(String dbName) {
		Collection<ComparableTerm> terms = BioSql.getTermsWithCrossRef(this.getCrossRef());
		Collection<OntologyOntologyAnnotBJ> result = new ArrayList<OntologyOntologyAnnotBJ>(terms.size());
		for (ComparableTerm term : terms) {
			OntologyOntologyAnnotBJ annotation = new OntologyOntologyAnnotBJ(term);
			if (annotation.getOntology().getDatabaseName().equals(dbName)) {
				result.add(annotation);
			}
		}
		return result;
	}

	@Override
	public Collection<OntologyOntologyAnnotBJ> getOntologyAnnots(AnnotationFilter<OntologyOntologyAnnotBJ> filter) {
		Collection<ComparableTerm> terms = BioSql.getTermsWithCrossRef(this.getCrossRef());
		Collection<OntologyOntologyAnnotBJ> result = new ArrayList<OntologyOntologyAnnotBJ>(terms.size());
		for (ComparableTerm term : terms) {
			OntologyOntologyAnnotBJ annotation = new OntologyOntologyAnnotBJ(term);
			if (filter.accept(annotation)) {
				result.add(annotation);
			}
		}
		return result;
	}

	@Override
	public AnnotationMethodBJ getMethodInstance(String method) {
		return AnnotationMethodBJ.getInstance(method);
	}

	///////
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
	public Collection<OntologyOntologyAnnotBJ> getDBLinkAnnots(AnnotationMethodBJ method, String dbName,
			String accession) {
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

}
